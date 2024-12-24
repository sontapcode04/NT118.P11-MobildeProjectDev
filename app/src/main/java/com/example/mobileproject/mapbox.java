package com.example.mobileproject;

import static com.mapbox.maps.plugin.animation.CameraAnimationsUtils.getCamera;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.addOnMapClickListener;
import static com.mapbox.maps.plugin.gestures.GesturesUtils.getGestures;
import static com.mapbox.maps.plugin.locationcomponent.LocationComponentUtils.getLocationComponent;
import static com.mapbox.navigation.base.extensions.RouteOptionsExtensions.applyDefaultNavigationOptions;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.mobileproject.api.ApiService;
import com.example.mobileproject.api.RetrofitClient;

import com.example.mobileproject.model.PotholeProximityDetector;
import com.example.mobileproject.model.PotholeResponse;

import com.example.mobileproject.model.PotholeData;
import com.example.mobileproject.model.PotholeDetector;
import com.example.mobileproject.model.PotholeRepository;

import com.example.mobileproject.model.detectedPotholeRequest;
import com.example.mobileproject.model.detectedPotholeResponse;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.gestures.MoveGestureDetector;
import com.mapbox.api.directions.v5.DirectionsCriteria;
import com.mapbox.api.directions.v5.models.Bearing;
import com.mapbox.api.directions.v5.models.RouteOptions;
import com.mapbox.api.directions.v5.models.VoiceInstructions;
import com.mapbox.bindgen.Expected;
import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.EdgeInsets;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.extension.style.layers.properties.generated.TextAnchor;
import com.mapbox.maps.plugin.animation.MapAnimationOptions;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.AnnotationPluginImplKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotation;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManagerKt;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.gestures.OnMapClickListener;
import com.mapbox.maps.plugin.gestures.OnMoveListener;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentConstants;
import com.mapbox.maps.plugin.locationcomponent.LocationComponentPlugin;
import com.mapbox.maps.plugin.locationcomponent.generated.LocationComponentSettings;
import com.mapbox.navigation.base.formatter.DistanceFormatter;
import com.mapbox.navigation.base.formatter.UnitType;
import com.mapbox.navigation.base.options.NavigationOptions;
import com.mapbox.navigation.base.route.NavigationRoute;
import com.mapbox.navigation.base.route.NavigationRouterCallback;
import com.mapbox.navigation.base.route.RouterFailure;
import com.mapbox.navigation.base.route.RouterOrigin;
import com.mapbox.navigation.core.MapboxNavigation;
import com.mapbox.navigation.core.directions.session.RoutesObserver;
import com.mapbox.navigation.core.directions.session.RoutesUpdatedResult;
import com.mapbox.navigation.core.lifecycle.MapboxNavigationApp;
import com.mapbox.navigation.core.trip.session.LocationMatcherResult;
import com.mapbox.navigation.core.trip.session.LocationObserver;
import com.mapbox.navigation.core.trip.session.VoiceInstructionsObserver;
import com.mapbox.navigation.ui.base.util.MapboxNavigationConsumer;
import com.mapbox.navigation.ui.maps.location.NavigationLocationProvider;
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineApi;
import com.mapbox.navigation.ui.maps.route.line.api.MapboxRouteLineView;
import com.mapbox.navigation.ui.maps.route.line.model.MapboxRouteLineOptions;
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineError;
import com.mapbox.navigation.ui.maps.route.line.model.RouteLineResources;
import com.mapbox.navigation.ui.maps.route.line.model.RouteSetValue;
import com.mapbox.navigation.ui.voice.api.MapboxSpeechApi;
import com.mapbox.navigation.ui.voice.api.MapboxVoiceInstructionsPlayer;
import com.mapbox.navigation.ui.voice.model.SpeechAnnouncement;
import com.mapbox.navigation.ui.voice.model.SpeechError;
import com.mapbox.navigation.ui.voice.model.SpeechValue;
import com.mapbox.navigation.ui.voice.model.SpeechVolume;
import com.mapbox.navigation.ui.voice.view.MapboxSoundButton;
import com.mapbox.search.autocomplete.PlaceAutocomplete;
import com.mapbox.search.autocomplete.PlaceAutocompleteSuggestion;
import com.mapbox.search.ui.adapter.autocomplete.PlaceAutocompleteUiAdapter;
import com.mapbox.search.ui.view.CommonSearchViewConfiguration;
import com.mapbox.search.ui.view.SearchResultsView;

import com.mapbox.navigation.ui.maneuver.api.MapboxManeuverApi;
import com.mapbox.navigation.ui.maneuver.model.Maneuver;

import com.mapbox.navigation.ui.maneuver.view.MapboxManeuverView;
import com.mapbox.navigation.ui.maneuver.api.MapboxManeuverApi;
import com.mapbox.navigation.ui.maneuver.model.ManeuverError;
import com.mapbox.navigation.base.trip.model.RouteProgress;
import com.mapbox.navigation.core.trip.session.RouteProgressObserver;
import com.mapbox.navigation.ui.maneuver.model.PrimaryManeuver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mapbox extends AppCompatActivity {

    MapView mapView;
    FloatingActionButton setRoute;
    FloatingActionButton focusLocationBtn;
    private final NavigationLocationProvider navigationLocationProvider = new NavigationLocationProvider();
    private MapboxRouteLineView routeLineView;
    private MapboxRouteLineApi routeLineApi;
    private final LocationObserver locationObserver = new LocationObserver() {
        @Override
        public void onNewRawLocation(@NonNull Location location) {
            // Không cần xử lý raw location
        }

        @Override
        public void onNewLocationMatcherResult(@NonNull LocationMatcherResult locationMatcherResult) {
            Location location = locationMatcherResult.getEnhancedLocation();
            navigationLocationProvider.changePosition(location, locationMatcherResult.getKeyPoints(), null, null);

            // Cập nhật location cho cả hai detector
            new Thread(() -> {
                try {
                    // Cập nhật PotholeDetector để phát hiện ổ gà mới
                    potholeDetector.updateLocation(location);

                    // Cập nhật PotholeProximityDetector để cảnh báo ổ gà gần đó
                    potholeProximityDetector.updateLocation(location);
                } catch (Exception e) {
                    Log.e("MainActivity", "Error updating location for detectors", e);
                }
            }).start();

            if (focusLocation) {
                updateCamera(Point.fromLngLat(location.getLongitude(), location.getLatitude()),
                        (double) location.getBearing());
            }
        }
    };
    private final RoutesObserver routesObserver = new RoutesObserver() {
        @Override
        public void onRoutesChanged(@NonNull RoutesUpdatedResult routesUpdatedResult) {
            routeLineApi.setNavigationRoutes(routesUpdatedResult.getNavigationRoutes(), new MapboxNavigationConsumer<Expected<RouteLineError, RouteSetValue>>() {
                @Override
                public void accept(Expected<RouteLineError, RouteSetValue> routeLineErrorRouteSetValueExpected) {
                    Style style = mapView.getMapboxMap().getStyle();
                    if (style != null) {
                        routeLineView.renderRouteDrawData(style, routeLineErrorRouteSetValueExpected);
                    }
                }
            });
        }
    };
    boolean focusLocation = true;
    private MapboxNavigation mapboxNavigation;
    private void updateCamera(Point point, Double bearing) {
        MapAnimationOptions animationOptions = new MapAnimationOptions.Builder().duration(1500L).build();
        CameraOptions cameraOptions = new CameraOptions.Builder().center(point).zoom(18.0).bearing(bearing).pitch(45.0)
                .padding(new EdgeInsets(1000.0, 0.0, 0.0, 0.0)).build();

        getCamera(mapView).easeTo(cameraOptions, animationOptions);
    }
    private final OnMoveListener onMoveListener = new OnMoveListener() {
        @Override
        public void onMoveBegin(@NonNull MoveGestureDetector moveGestureDetector) {
            focusLocation = false;
            getGestures(mapView).removeOnMoveListener(this);
            focusLocationBtn.show();
        }

        @Override
        public boolean onMove(@NonNull MoveGestureDetector moveGestureDetector) {
            return false;
        }

        @Override
        public void onMoveEnd(@NonNull MoveGestureDetector moveGestureDetector) {

        }
    };
    private final ActivityResultLauncher<String> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
        @Override
        public void onActivityResult(Boolean result) {
            if (result) {
                Toast.makeText(mapbox.this, "Permission granted! Restart this app", Toast.LENGTH_SHORT).show();
            }
        }
    });

    private MapboxSpeechApi speechApi;
    private MapboxVoiceInstructionsPlayer mapboxVoiceInstructionsPlayer;

    private MapboxNavigationConsumer<Expected<SpeechError, SpeechValue>> speechCallback = new MapboxNavigationConsumer<Expected<SpeechError, SpeechValue>>() {
        @Override
        public void accept(Expected<SpeechError, SpeechValue> speechErrorSpeechValueExpected) {
            speechErrorSpeechValueExpected.fold(new Expected.Transformer<SpeechError, Unit>() {
                @NonNull
                @Override
                public Unit invoke(@NonNull SpeechError input) {
                    mapboxVoiceInstructionsPlayer.play(input.getFallback(), voiceInstructionsPlayerCallback);
                    return Unit.INSTANCE;
                }
            }, new Expected.Transformer<SpeechValue, Unit>() {
                @NonNull
                @Override
                public Unit invoke(@NonNull SpeechValue input) {
                    mapboxVoiceInstructionsPlayer.play(input.getAnnouncement(), voiceInstructionsPlayerCallback);
                    return Unit.INSTANCE;
                }
            });
        }
    };

    private MapboxNavigationConsumer<SpeechAnnouncement> voiceInstructionsPlayerCallback = new MapboxNavigationConsumer<SpeechAnnouncement>() {
        @Override
        public void accept(SpeechAnnouncement speechAnnouncement) {
            speechApi.clean(speechAnnouncement);
        }
    };

    VoiceInstructionsObserver voiceInstructionsObserver = new VoiceInstructionsObserver() {
        @Override
        public void onNewVoiceInstructions(@NonNull VoiceInstructions voiceInstructions) {
            speechApi.generate(voiceInstructions, speechCallback);
        }
    };

    private boolean isVoiceInstructionsMuted = false;
    private PlaceAutocomplete placeAutocomplete;
    private SearchResultsView searchResultsView;
    private PlaceAutocompleteUiAdapter placeAutocompleteUiAdapter;
    private TextInputEditText searchET;
    private boolean ignoreNextQueryUpdate = false;

    private PotholeDetector potholeDetector;
    private PointAnnotationManager pointAnnotationManager;
    private Bitmap potholeBitmap;
    private Bitmap locationBitmap;

    private PotholeProximityDetector potholeProximityDetector;

    private CardView maneuverCardView;
    private MapboxManeuverView maneuverView;
    private TextView stepDistanceRemainingTextView;
    private TextView stepInstructionTextView;

    private MapboxManeuverApi maneuverApi;

    private Map<Long, PotholeData> potholeDataMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mapbox);

        mapView = findViewById(R.id.mapView);
        focusLocationBtn = findViewById(R.id.focusLocation);
        setRoute = findViewById(R.id.setRoute);

        MapboxRouteLineOptions options = new MapboxRouteLineOptions.Builder(this).withRouteLineResources(new RouteLineResources.Builder().build())
                .withRouteLineBelowLayerId(LocationComponentConstants.LOCATION_INDICATOR_LAYER).build();
        routeLineView = new MapboxRouteLineView(options);
        routeLineApi = new MapboxRouteLineApi(options);

        speechApi = new MapboxSpeechApi(mapbox.this, getString(R.string.mapbox_access_token), Locale.US.toLanguageTag());
        mapboxVoiceInstructionsPlayer = new MapboxVoiceInstructionsPlayer(mapbox.this, Locale.US.toLanguageTag());

        NavigationOptions navigationOptions = new NavigationOptions.Builder(this)
                .accessToken(getString(R.string.mapbox_access_token))

                .build();

        MapboxNavigationApp.setup(navigationOptions);
        mapboxNavigation = new MapboxNavigation(navigationOptions);

        mapboxNavigation.registerRoutesObserver(routesObserver);
        mapboxNavigation.registerLocationObserver(locationObserver);
        mapboxNavigation.registerVoiceInstructionsObserver(voiceInstructionsObserver);

        placeAutocomplete = PlaceAutocomplete.create(getString(R.string.mapbox_access_token));
        searchET = findViewById(R.id.searchET);

        searchResultsView = findViewById(R.id.search_results_view);
        searchResultsView.initialize(new SearchResultsView.Configuration(new CommonSearchViewConfiguration()));

        placeAutocompleteUiAdapter = new PlaceAutocompleteUiAdapter(searchResultsView, placeAutocomplete, LocationEngineProvider.getBestLocationEngine(mapbox.this));

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (ignoreNextQueryUpdate) {
                    ignoreNextQueryUpdate = false;
                } else {
                    placeAutocompleteUiAdapter.search(charSequence.toString(), new Continuation<Unit>() {
                        @NonNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NonNull Object o) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    searchResultsView.setVisibility(View.VISIBLE);
                                }
                            });
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        MapboxSoundButton soundButton = findViewById(R.id.soundButton);
        soundButton.unmute();
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isVoiceInstructionsMuted = !isVoiceInstructionsMuted;
                if (isVoiceInstructionsMuted) {
                    soundButton.muteAndExtend(1500L);
                    mapboxVoiceInstructionsPlayer.volume(new SpeechVolume(0f));
                } else {
                    soundButton.unmuteAndExtend(1500L);
                    mapboxVoiceInstructionsPlayer.volume(new SpeechVolume(1f));
                }
            }
        });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.POST_NOTIFICATIONS},
                        1);
            }
        }

        if (ActivityCompat.checkSelfPermission(mapbox.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(mapbox.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            activityResultLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            activityResultLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            mapboxNavigation.startTripSession();
        }

        focusLocationBtn.hide();
        LocationComponentPlugin locationComponentPlugin = getLocationComponent(mapView);
        getGestures(mapView).addOnMoveListener(onMoveListener);

        setRoute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mapbox.this, "Please select a location in map", Toast.LENGTH_SHORT).show();
            }
        });



        mapView.getMapboxMap().loadStyleUri(Style.MAPBOX_STREETS, style -> {
            initializePointAnnotationManager();
        });
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location);
        AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
        PointAnnotationManager pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(annotationPlugin, mapView);
        addOnMapClickListener(mapView.getMapboxMap(), new OnMapClickListener() {
            @Override
            public boolean onMapClick(@NonNull Point point) {
                // Kiểm tra xem có click vào pothole không
                PointAnnotation clickedPothole = null;
                double minDistance = 0.0001; // Khoảng cách tối thiểu tính bằng độ (degrees)

                List<PointAnnotation> annotations = pointAnnotationManager.getAnnotations();
                for (PointAnnotation annotation : annotations) {
                    Point annotationPoint = annotation.getPoint();

                    // Tính khoảng cách giữa điểm click và marker
                    double distance = Math.sqrt(
                            Math.pow(annotationPoint.longitude() - point.longitude(), 2) +
                                    Math.pow(annotationPoint.latitude() - point.latitude(), 2)
                    );

                    if (distance < minDistance) {
                        clickedPothole = annotation;
                        break;
                    }
                }

                if (clickedPothole != null) {
                    // Nếu click vào pothole, hiển thị dialog
                    PotholeData potholeData = potholeDataMap.get(clickedPothole.getId());
                    if (potholeData != null) {
                        showPotholeDetails(potholeData);
                    }
                } else {
                    // Nếu click vào vị trí khác, xử lý set route
                    pointAnnotationManager.deleteAll();
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.location);
                    PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                            .withTextAnchor(TextAnchor.CENTER)
                            .withIconImage(bitmap)
                            .withPoint(point);
                    pointAnnotationManager.create(pointAnnotationOptions);

                    setRoute.setOnClickListener(view -> fetchRoute(point));
                }
                return true;
            }
        });
        focusLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                focusLocation = true;
                getGestures(mapView).addOnMoveListener(onMoveListener);
                focusLocationBtn.hide();
            }
        });

        placeAutocompleteUiAdapter.addSearchListener(new PlaceAutocompleteUiAdapter.SearchListener() {
            @Override
            public void onSuggestionsShown(@NonNull List<PlaceAutocompleteSuggestion> list) {

            }

            @Override
            public void onSuggestionSelected(@NonNull PlaceAutocompleteSuggestion placeAutocompleteSuggestion) {
                ignoreNextQueryUpdate = true;
                focusLocation = false;
                searchET.setText(placeAutocompleteSuggestion.getName());
                searchResultsView.setVisibility(View.GONE);

                pointAnnotationManager.deleteAll();
                PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions().withTextAnchor(TextAnchor.CENTER).withIconImage(bitmap)
                        .withPoint(placeAutocompleteSuggestion.getCoordinate());
                pointAnnotationManager.create(pointAnnotationOptions);
                updateCamera(placeAutocompleteSuggestion.getCoordinate(), 0.0);

                setRoute.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        fetchRoute(placeAutocompleteSuggestion.getCoordinate());
                    }
                });
            }

            @Override
            public void onPopulateQueryClick(@NonNull PlaceAutocompleteSuggestion placeAutocompleteSuggestion) {
                //queryEditText.setText(placeAutocompleteSuggestion.getName());
            }

            @Override
            public void onError(@NonNull Exception e) {

            }
        });

        potholeDetector = new PotholeDetector(this, new PotholeDetector.PotholeDetectorCallback() {
            @Override
            public void onPotholeDetected(Point location) {
                runOnUiThread(() -> {
                    PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                            .withTextAnchor(TextAnchor.CENTER)
                            .withIconImage(potholeBitmap)
                            .withPoint(location);
                    pointAnnotationManager.create(pointAnnotationOptions);

                    Toast.makeText(mapbox.this, "Pothole detected!", Toast.LENGTH_SHORT).show();
                });
            }
        });

        potholeProximityDetector = new PotholeProximityDetector(this);

        maneuverCardView = findViewById(R.id.maneuverCardView);
        maneuverView = findViewById(R.id.maneuverView);
        stepDistanceRemainingTextView = findViewById(R.id.stepDistanceRemainingTextView);
        stepInstructionTextView = findViewById(R.id.stepInstructionTextView);

        maneuverApi = new MapboxManeuverApi(
                new DistanceFormatter() {
                    @NonNull
                    @Override
                    public SpannableString formatDistance(double v) {
                        return null;
                    }

                    @NonNull

                    public String format(double distanceInMeters) {
                        if (distanceInMeters < 1000) {
                            return String.format(Locale.getDefault(), "%d m", (int) distanceInMeters);
                        } else {
                            return String.format(Locale.getDefault(), "%.1f km", distanceInMeters / 1000);
                        }
                    }

                    @NonNull
                    public UnitType getUnitType() {
                        return UnitType.METRIC;
                    }
                }
        );

        mapboxNavigation.registerRouteProgressObserver(new RouteProgressObserver() {
            @Override
            public void onRouteProgressChanged(@NonNull RouteProgress routeProgress) {
                maneuverCardView.setVisibility(View.VISIBLE);

                Expected<ManeuverError, List<Maneuver>> maneuvers = maneuverApi.getManeuvers(routeProgress);
                maneuvers.fold(
                        error -> Unit.INSTANCE,
                        maneuverList -> {
                            if (!maneuverList.isEmpty()) {
                                Maneuver maneuver = maneuverList.get(0);
                                PrimaryManeuver primary = maneuver.getPrimary();

                                double distanceRemaining = routeProgress.getCurrentLegProgress().getCurrentStepProgress().getDistanceRemaining();
                                String formattedDistance = formatDistance(distanceRemaining);
                                stepDistanceRemainingTextView.setText(formattedDistance);

                                if (primary != null) {
                                    String instruction = primary.getText();
                                    if (instruction != null) {
                                        stepInstructionTextView.setText(instruction);
                                        maneuverView.renderManeuvers(maneuvers);
                                    }
                                }
                            }
                            return Unit.INSTANCE;
                        }
                );
            }
        });

        LinearLayout homeLayout = findViewById(R.id.home);
        homeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mapbox.this, dashboard.class);
                startActivity(intent);
            }
        });

        LinearLayout settingLayout = findViewById(R.id.setting);
        settingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mapbox.this, setting.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        potholeProximityDetector.reset();
        potholeDetector.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        potholeDetector.stop();
    }

    @SuppressLint("MissingPermission")
    private void fetchRoute(Point point) {
        LocationEngine locationEngine = LocationEngineProvider.getBestLocationEngine(mapbox.this);
        locationEngine.getLastLocation(new LocationEngineCallback<LocationEngineResult>() {
            @Override
            public void onSuccess(LocationEngineResult result) {
                Location location = result.getLastLocation();
                setRoute.setEnabled(false);
                RouteOptions.Builder builder = RouteOptions.builder();
                Point origin = Point.fromLngLat(Objects.requireNonNull(location).getLongitude(), location.getLatitude());
                builder.coordinatesList(Arrays.asList(origin, point));
                builder.alternatives(false);
                builder.profile(DirectionsCriteria.PROFILE_DRIVING);
                builder.bearingsList(Arrays.asList(Bearing.builder().angle(location.getBearing()).degrees(45.0).build(), null));
                applyDefaultNavigationOptions(builder);

                mapboxNavigation.requestRoutes(builder.build(), new NavigationRouterCallback() {
                    @Override
                    public void onRoutesReady(@NonNull List<NavigationRoute> list, @NonNull RouterOrigin routerOrigin) {
                        mapboxNavigation.setNavigationRoutes(list);
                        focusLocationBtn.performClick();
                        setRoute.setEnabled(true);
                    }

                    @Override
                    public void onFailure(@NonNull List<RouterFailure> list, @NonNull RouteOptions routeOptions) {
                        setRoute.setEnabled(true);
                        Toast.makeText(mapbox.this, "Route request failed", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCanceled(@NonNull RouteOptions routeOptions, @NonNull RouterOrigin routerOrigin) {

                    }
                });
            }

            @Override
            public void onFailure(@NonNull Exception exception) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mapboxNavigation != null) {
            mapboxNavigation.onDestroy();
            mapboxNavigation = null;
        }
        mapboxNavigation.unregisterRoutesObserver(routesObserver);
        mapboxNavigation.unregisterLocationObserver(locationObserver);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mapboxNavigation != null) {
            mapboxNavigation.unregisterLocationObserver(locationObserver);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mapboxNavigation != null) {
            mapboxNavigation.registerLocationObserver(locationObserver);
        }
    }

    private Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (drawable == null) {
            return null;
        }

        Bitmap bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    private void loadPotholes() {
        PotholeRepository.getInstance().getPotholes(new PotholeRepository.PotholeCallback() {
            @Override
            public void onSuccess(List<PotholeData> potholes) {
                runOnUiThread(() -> {
                    for (PotholeData pothole : potholes) {
                        Point point = Point.fromLngLat(pothole.getLongitude(), pothole.getLatitude());

                        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                                .withPoint(point)
                                .withIconImage(potholeBitmap)
                                .withTextColor("red")
                                .withTextSize(12.0);

                        pointAnnotationManager.create(pointAnnotationOptions);
                    }

                    if (!potholes.isEmpty()) {
                        List<Point> points = new ArrayList<>();
                        for (PotholeData pothole : potholes) {
                            points.add(Point.fromLngLat(pothole.getLongitude(), pothole.getLatitude()));
                        }

                        CameraOptions cameraPosition = mapView.getMapboxMap().cameraForCoordinates(
                                points,
                                new EdgeInsets(50.0, 50.0, 50.0, 50.0),
                                null,
                                null
                        );
                        mapView.getMapboxMap().setCamera(cameraPosition);
                    }
                });
            }

            @Override
            public void onError(String message) {
                runOnUiThread(() -> {
                    Toast.makeText(mapbox.this,
                            "Error loading potholes: " + message,
                            Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loadPotholesFromServer() {
        Log.d("MainActivity", "Starting to load potholes from server");
        PotholeRepository.getInstance().getPotholes(new PotholeRepository.PotholeCallback() {
            @Override
            public void onSuccess(List<PotholeData> potholes) {
                Log.d("MainActivity", "Successfully loaded " + potholes.size() + " potholes");

                potholeProximityDetector.updatePotholes(potholes);

                runOnUiThread(() -> {
                    pointAnnotationManager.deleteAll();
                    potholeDataMap.clear();

                    for (PotholeData pothole : potholes) {
                        Point point = Point.fromLngLat(pothole.getLongitude(), pothole.getLatitude());
                        Log.d("MainActivity", "Creating marker at: " + point.toString());

                        PointAnnotationOptions pointAnnotationOptions = new PointAnnotationOptions()
                                .withPoint(point)
                                .withIconImage("pothole-marker")
                                .withIconSize(1.5f);

                        PointAnnotation annotation = pointAnnotationManager.create(pointAnnotationOptions);
                        potholeDataMap.put(annotation.getId(), pothole);
                    }

                    pointAnnotationManager.addClickListener(annotation -> {
                        PotholeData clickedPothole = potholeDataMap.get(annotation.getId());
                        if (clickedPothole != null) {
                            showPotholeDetails(clickedPothole);
                        }
                        return true;
                    });

                    if (!potholes.isEmpty()) {
                        List<Point> points = new ArrayList<>();
                        for (PotholeData pothole : potholes) {
                            points.add(Point.fromLngLat(pothole.getLongitude(), pothole.getLatitude()));
                        }

                        try {
                            CameraOptions cameraPosition = mapView.getMapboxMap().cameraForCoordinates(
                                    points,
                                    new EdgeInsets(50.0, 50.0, 50.0, 50.0),
                                    null,
                                    null
                            );
                            mapView.getMapboxMap().setCamera(cameraPosition);
                        } catch (Exception e) {
                            Log.e("MainActivity", "Error setting camera: " + e.getMessage());
                        }
                    }
                });
            }

            @Override
            public void onError(String message) {
                Log.e("MainActivity", "Error loading potholes: " + message);
                runOnUiThread(() -> {
                    Toast.makeText(mapbox.this,
                            "Error loading potholes: " + message,
                            Toast.LENGTH_LONG).show();
                });
            }
        });
    }

    private void initializePointAnnotationManager() {
        mapView.getMapboxMap().getStyle(style -> {
            AnnotationPlugin annotationPlugin = AnnotationPluginImplKt.getAnnotations(mapView);
            pointAnnotationManager = PointAnnotationManagerKt.createPointAnnotationManager(
                    annotationPlugin,
                    mapView
            );

            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.warning_pothole);
            if (drawable != null) {
                Bitmap bitmap = Bitmap.createBitmap(48, 48, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                drawable.draw(canvas);
                potholeBitmap = bitmap;

                style.addImage("pothole-marker", bitmap);

                loadPotholesFromServer();
            } else {
                Log.e("MainActivity", "Could not load pothole marker drawable");
            }
        });
    }

    private String formatDistance(double distanceInMeters) {
        if (distanceInMeters < 1000) {
            return String.format(Locale.getDefault(), "%d m", (int) distanceInMeters);
        } else {
            return String.format(Locale.getDefault(), "%.1f km", distanceInMeters / 1000);
        }
    }

    private void showPotholeDetails(PotholeData pothole) {
        PotholeDetailDialog dialog = new PotholeDetailDialog(this, pothole);
        dialog.show();
    }
}