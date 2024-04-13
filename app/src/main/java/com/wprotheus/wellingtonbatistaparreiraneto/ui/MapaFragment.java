package com.wprotheus.wellingtonbatistaparreiraneto.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.wprotheus.wellingtonbatistaparreiraneto.R;
import com.wprotheus.wellingtonbatistaparreiraneto.databinding.FragmentMapaBinding;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.gestures.RotationGestureOverlay;

public class MapaFragment extends Fragment {
    private FragmentMapaBinding mapaBinding;
    private GeoPoint marcadorMapa = new GeoPoint(-10.183114920492931, -48.333664189415735);
    private Marker pointer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Configuration.getInstance().load(getContext(), PreferenceManager.getDefaultSharedPreferences(getContext()));
        mapaBinding = FragmentMapaBinding.inflate(inflater, container, false);
        View view = mapaBinding.getRoot();
        configuraViewMapa();

        pointer.setOnMarkerDragListener(new Marker.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                GeoPoint geoPoint = marker.getPosition();
                geoPoint.setCoords(geoPoint.getLatitude(), geoPoint.getLongitude());
                marker.setTitle(getString(R.string.coord_geo));
                marker.setSnippet(getString(R.string.lattde) + geoPoint.getLatitude() +
                        getString(R.string.lgtde) + geoPoint.getLongitude());
                mapaBinding.mvMapa.getOverlays().add(marker);
                mapaBinding.mvMapa.invalidate();
            }

            @Override
            public void onMarkerDragStart(Marker marker) {
            }
        });
        return view;
    }

    private void configuraViewMapa() {
        mapaBinding.mvMapa.getRootView();
        mapaBinding.mvMapa.setTileSource(TileSourceFactory.MAPNIK);
        mapaBinding.mvMapa.getController().setZoom(15.2);
        mapaBinding.mvMapa.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.ALWAYS);
        mapaBinding.mvMapa.setMultiTouchControls(true);
        RotationGesture(mapaBinding.mvMapa);
        marcadorInicial();
    }

    private void marcadorInicial() {
        Drawable icon = ResourcesCompat.getDrawable(getResources(), R.drawable.sunflower, null);
        pointer = new Marker(mapaBinding.mvMapa);
        pointer.setPosition(marcadorMapa);
        pointer.setIcon(icon);
        pointer.setDraggable(true);
        pointer.setTitle(getString(R.string.prc_gira));
        pointer.setSnippet(getString(R.string.lattde) + marcadorMapa.getLatitude() +
                getString(R.string.lgtde) + marcadorMapa.getLongitude());
        pointer.showInfoWindow();
        pointer.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER);
        mapaBinding.mvMapa.getOverlays().add(pointer);
        mapaBinding.mvMapa.getController().setCenter(marcadorMapa);
        mapaBinding.mvMapa.invalidate();
    }

    private void RotationGesture(MapView map) {
        RotationGestureOverlay mRotationGestureOverlay = new RotationGestureOverlay(map);
        mRotationGestureOverlay.setEnabled(true);
        map.setMultiTouchControls(true);
        map.getOverlays().add(mRotationGestureOverlay);
    }
}