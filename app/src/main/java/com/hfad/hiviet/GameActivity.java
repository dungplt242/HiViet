package com.hfad.hiviet;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GameActivity extends FragmentActivity implements OnMapReadyCallback {

    // views
    private TextView mTimerTextView;
    private TextView mDialogueTextView;

    // data
    private GoogleMap mMap;
    private List<Attraction> attractionList;

    // game configurations
    // all time durations are in ms
    private final int numRounds = 5;
    private final int roundTime = 10000;
    private final int delayBetweenRounds = 3000;

    // game states
    private int currentScore;
    private int currentRound;
    private boolean hasShownResult;

    // game graphics
    private Attraction currentAttraction;
    private Circle currentCircle;
    private Marker guessMarker;
    private Marker targetMarker;

    // utilities
    private DecimalFormat df = new DecimalFormat("#");
    private Timer timer;
    private CountDownTimer countDownTimer;

    private GoogleMap.OnMapClickListener mapOnClickListener = new GoogleMap.OnMapClickListener() {

        private LatLng guessedPoint;
        private LatLng targetPoint;

        @Override
        public void onMapClick(LatLng guessedPoint) {
            if (!hasShownResult) {
                this.guessedPoint = guessedPoint;
                this.targetPoint = currentAttraction.getLocation();
                countDownTimer.cancel();
                showResult();
                evaluateResult();
                timer.schedule(prepareNewRound(), delayBetweenRounds);
            }
        }

        void removeDrewObjects() {
            currentCircle.remove();
            guessMarker.remove();
            targetMarker.remove();
        }

        TimerTask prepareNewRound() {
            return new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            removeDrewObjects();
                        }
                    });
                    timer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            startRound();
                        }
                    }, 0);
                }
            };
        }

        private void evaluateResult() {
            final int METERS_TO_KILOMETERS = 1000;
            final int LARGEST_DISTANCE_TO_UNLOCK = 100; //in km
            double distanceError = calculateDistance(guessedPoint, targetPoint);
            currentCircle = mMap.addCircle(new CircleOptions().center(targetPoint)
                    .strokeWidth(8)
                    .strokeColor(Color.RED)
                    .radius(METERS_TO_KILOMETERS * distanceError));
            currentScore += calculateScore(distanceError);
            mDialogueTextView.setText(String.format(getString(R.string.guess_text),
                    df.format(distanceError), currentScore));
            if (distanceError <= LARGEST_DISTANCE_TO_UNLOCK && !currentAttraction.isUnlocked())
                handlingUnlockEvent();
        }

        private void handlingUnlockEvent() {
            congratulationMessage();
            currentAttraction.unlock();
            storeUnlockedAttraction();
        }

        private void congratulationMessage() {
            FragmentManager fragmentManager = getSupportFragmentManager();
            final CongratFragment dialogueFragmentItem =
                    CongratFragment.newInstance(currentAttraction.getId());
            dialogueFragmentItem.show(fragmentManager, null);
            final Handler handler  = new Handler();
            final Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    dialogueFragmentItem.dismiss();
                }
            };
            handler.postDelayed(runnable, 2000);
        }

        private void storeUnlockedAttraction() {
            try {
                PrintStream unlockedFile = new PrintStream(openFileOutput(
                        getString(R.string.unlocked_file_name), MODE_APPEND));
                currentAttraction.storeAsUnlocked(unlockedFile);
                unlockedFile.close();
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        private void showResult() {
            hasShownResult = true;
            guessMarker = mMap.addMarker(new MarkerOptions().position(guessedPoint));
            targetMarker = mMap.addMarker(new MarkerOptions().position(targetPoint));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        loadAttractionForGame();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        initComponents();
    }

    private void loadAttractionForGame() {
        attractionList = AttractionList.getRandomAttractions(numRounds);
    }

    private void initComponents() {
        mTimerTextView = findViewById(R.id.timerText);
        mDialogueTextView = findViewById(R.id.dialogue_text);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mapSettings();
        setupCamera();
        startGame();
    }

    private void mapSettings() {
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.game_map_style));
        mMap.getUiSettings().setAllGesturesEnabled(false);
        mMap.setOnMapClickListener(mapOnClickListener);
    }

    private void setupCamera() {
        CameraPosition cameraPosition = new CameraPosition.Builder().
                target(new LatLng(16.199493,105.3424463)).zoom(5.6f).build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    private void startGame() {
        countDownTimer = new CountDownTimer(roundTime, 1000) {
            @Override
            public void onTick(long currentTime) {
                mTimerTextView.setText(String.valueOf(currentTime/1000+1));
            }

            @Override
            public void onFinish() {
                // if execution reached this point,
                // the player hadn't made a guess last round
                hasShownResult = true;
                LatLng targetPoint = currentAttraction.getLocation();
                targetMarker = mMap.addMarker(new MarkerOptions().position(targetPoint));

                mDialogueTextView.setText(String.format(getString(R.string.time_out_text), currentScore));
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                targetMarker.remove();
                            }
                        });
                        startRound();
                    }
                }, delayBetweenRounds);
            }
        };
        currentScore = 0;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startRound();
            }
        }, 0);
    }

    // WARNING: not meant to be run on main thread
    private void startRound() {
        if (currentRound < numRounds) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    currentAttraction = attractionList.get(currentRound);
                    mDialogueTextView.setText(String.format(getString(R.string.find_attraction),
                            currentAttraction.getTitle()));
                }
            });
            ++currentRound;
            hasShownResult = false;
            countDownTimer.start();
        }
        else {
            // the current game is finished
            runOnUiThread(displayScore());
            timer.schedule(summarizeGame(), 2000);
        }
    }

    TimerTask summarizeGame() {
        return new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        };
    }

    Runnable displayScore() {
        return new Runnable() {
            @Override
            public void run() {
                mDialogueTextView.setText(String.format(getString(R.string.total_score),
                        currentScore));
            }
        };
    }

    // score is in range [0..100]
    private int calculateScore(double distance) {
        // TODO: replace with a real function
        return (int) Math.round(Math.max(0, 100 - distance / 5));
    }

    // in kilometers
    public final static double AVERAGE_RADIUS_OF_EARTH_KM = 6371;
    public double calculateDistance(double userLat, double userLng,
                                            double venueLat, double venueLng) {

        double latDistance = Math.toRadians(userLat - venueLat);
        double lngDistance = Math.toRadians(userLng - venueLng);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(userLat)) * Math.cos(Math.toRadians(venueLat))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return AVERAGE_RADIUS_OF_EARTH_KM * c;
    }

    public double calculateDistance(LatLng source, LatLng dest) {
        return calculateDistance(source.latitude, source.longitude,
                dest.latitude, dest.longitude);
    }
}