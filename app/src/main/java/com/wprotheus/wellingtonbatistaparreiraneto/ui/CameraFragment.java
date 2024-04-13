package com.wprotheus.wellingtonbatistaparreiraneto.ui;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.wprotheus.wellingtonbatistaparreiraneto.R;
import com.wprotheus.wellingtonbatistaparreiraneto.databinding.FragmentCameraBinding;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CameraFragment extends Fragment
        implements ActivityCompat.OnRequestPermissionsResultCallback {
    private static final String PERMISSAO = Manifest.permission.CAMERA;
    private static final int CAMERA_REQUEST = 107;
    private static final int IMAGE_CAM = 71;
    private FragmentCameraBinding cameraBinding;
    private String photo;
    private Bitmap bitmap;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        cameraBinding = FragmentCameraBinding.inflate(inflater, container, false);
        abrirCamera();
        return cameraBinding.getRoot();
    }

    private void abrirCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, IMAGE_CAM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            bitmap = (Bitmap) Objects.requireNonNull(data.getExtras()).get("data");
            assert bitmap != null;
            photo = getEncodedString(bitmap);
            salvarImagem(bitmap);
        }
    }

    private String getEncodedString(Bitmap bitmap) {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
        byte[] imageArr = os.toByteArray();
        return Base64.encodeToString(imageArr, Base64.URL_SAFE);
    }

    private void salvarImagem(Bitmap bitmap) {
        @SuppressLint("SimpleDateFormat")
        String DATA_HORA = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String filename = "imagem" + DATA_HORA + ".jpg";
        File directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File file = new File(directory, filename);
        try {
            OutputStream fos = Files.newOutputStream(file.toPath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.close();
            Toast.makeText(getActivity(), "Imagem salva com sucesso", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(getActivity(), "Erro ao salvar imagem: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    public AlertDialog mensagemPermissao() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.requireActivity());
        builder.setTitle("Atenção")
                .setMessage(R.string.msg_permissao)
                .setCancelable(false)
                .setPositiveButton("OK", (dialog, which) -> ActivityCompat.requestPermissions(this.requireActivity(),
                        new String[]{PERMISSAO}, CAMERA_REQUEST))
                .setNegativeButton("NÃO", (dialog, which) -> {
                    Toast.makeText(this.requireActivity(), "O app será encerrado", Toast.LENGTH_SHORT).show();
                    this.requireActivity().finish();
                });
        return builder.create();
    }
}