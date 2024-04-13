package com.wprotheus.wellingtonbatistaparreiraneto.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.wprotheus.wellingtonbatistaparreiraneto.R;
import com.wprotheus.wellingtonbatistaparreiraneto.databinding.ActivityMainBinding;
import com.wprotheus.wellingtonbatistaparreiraneto.model.Stone;
import com.wprotheus.wellingtonbatistaparreiraneto.ui.CameraFragment;
import com.wprotheus.wellingtonbatistaparreiraneto.ui.MapaFragment;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.DadosViewModel;
import com.wprotheus.wellingtonbatistaparreiraneto.viewmodel.DataSetJSON;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String PERMISSAO = Manifest.permission.CAMERA;
    private static final int CAMERA_REQUEST = 107;
    private AppBarConfiguration mAppBarConfiguration;
    private DadosViewModel sharedDadosViewModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        sharedDadosViewModel = new ViewModelProvider(this).get(DadosViewModel.class);
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setImageTintList(null);
        binding.appBarMain.fab.setOnClickListener(view -> {
            solicitarPermissaoCamera();
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        navigationView.setItemIconTintList(null);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_elementos,
                R.id.nav_separacao, R.id.nav_finalizacao,
                R.id.nav_Camera, R.id.nav_mvMapa)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            int fragAtual = R.id.nav_mvMapa;
            if (fragAtual == R.id.nav_mvMapa) {
                if (id == R.id.nav_inicio)
                    navController.navigate(R.id.nav_inicio);
                if (id == R.id.nav_elementos)
                    navController.navigate(R.id.nav_elementos);
                if (id == R.id.nav_separacao)
                    navController.navigate(R.id.nav_separacao);
                if (id == R.id.nav_finalizacao)
                    navController.navigate(R.id.nav_finalizacao);
                drawer.closeDrawers();
                return true;
            } else {
                drawer.closeDrawers();
                return NavigationUI.onNavDestinationSelected(item, navController);
            }
        });
        executarApp();
    }

    public void solicitarPermissaoCamera() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, PERMISSAO)
                != PackageManager.PERMISSION_GRANTED)
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{PERMISSAO}, CAMERA_REQUEST);
        else
            iniciarCameraFragmento();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            iniciarCameraFragmento();
        else {
            ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, PERMISSAO);
            AlertDialog dialog = new CameraFragment().mensagemPermissao();
            dialog.show();
        }
    }

    public void iniciarCameraFragmento() {
        CameraFragment cameraFragment = new CameraFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.nav_host_fragment_content_main, cameraFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void executarApp() {
        ExecutorService service = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());
        service.execute(() -> {
            try {
                Stone tempo = new DataSetJSON().baixarJSON();
                if (tempo != null)
                    sharedDadosViewModel.getmLiveOriginal().postValue(tempo);
                handler.post(() -> {
                    Toast.makeText(getApplicationContext(), R.string.dados_baixados, Toast.LENGTH_SHORT).show();
                });
            } catch (Exception e) {
                e.printStackTrace();
                handler.post(() -> Toast.makeText(getApplicationContext(), R.string.problema_end, Toast.LENGTH_LONG).show());
            }
        });
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_mapa) {
            MapaFragment mapaFragment = new MapaFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment_content_main, mapaFragment).commit();
        }
        return super.onOptionsItemSelected(item);
    }
}