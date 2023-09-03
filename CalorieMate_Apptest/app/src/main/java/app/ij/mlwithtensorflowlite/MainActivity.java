package app.ij.mlwithtensorflowlite;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.HashMap;
import java.util.Map;

import app.ij.mlwithtensorflowlite.ml.Model;


public class MainActivity extends AppCompatActivity {

    Button camera, gallery;
    ImageView imageView;
    TextView result, confidence;
    int imageSize = 224;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camera = findViewById(R.id.button);
        gallery = findViewById(R.id.button2);

        result = findViewById(R.id.result);
        confidence = findViewById(R.id.confidence);
        imageView = findViewById(R.id.imageView);


        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent, 3);
                } else {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, 100);
                }
            }
        });
        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(cameraIntent, 1);
            }
        });
    }

    public void classifyImage(Bitmap image){
        try {
            Model model = Model.newInstance(getApplicationContext());

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            ByteBuffer byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3);
            byteBuffer.order(ByteOrder.nativeOrder());

            int[] intValues = new int[imageSize * imageSize];
            image.getPixels(intValues, 0, image.getWidth(), 0, 0, image.getWidth(), image.getHeight());
            int pixel = 0;
            //iterate over each pixel and extract R, G, and B values. Add those values individually to the byte buffer.
            for(int i = 0; i < imageSize; i ++){
                for(int j = 0; j < imageSize; j++){
                    int val = intValues[pixel++]; // RGB
                    byteBuffer.putFloat(((val >> 16) & 0xFF) * (1.f / 255));
                    byteBuffer.putFloat(((val >> 8) & 0xFF) * (1.f / 255));
                    byteBuffer.putFloat((val & 0xFF) * (1.f / 255));
                }
            }

            inputFeature0.loadBuffer(byteBuffer);

            // Runs model inference and gets result.
            Model.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

            float[] confidences = outputFeature0.getFloatArray();
            // find the index of the class with the biggest confidence.
            int maxPos = 0;
            float maxConfidence = 0;
            for (int i = 0; i < confidences.length; i++) {
                if (confidences[i] > maxConfidence) {
                    maxConfidence = confidences[i];
                    maxPos = i;
                }
            }
            //String[] classes = {"Alu Bhorta" , "Alu-Vaji" , "Begun Bhaja" , "Begun Bhorta" , "Biriyani" , "Boiled_egg" , "Borhani" , "Burger" , "Cake" , "cheesecake" , "Chicken_curry" , "Chicken_Grill" , "Chicken_wings" , "Chitoi  Pitha" , "Chocolate_cake" , "cup_cakes" , "Doi" , "Falooda" , "Fish Bhuna_Mach Bhuna" , "French_fries" , "Fried chicken - Murg Bhaja" , "Fried fish_Mach Bhaja" , "Fried_rice" , "Fuchka" , "Hilsha_Fish_Curry" , "ice_cream" , "Jalebi" , "Jorda" , "Kacchi" , "kebab - Gosht Kebab" , "Khichuri" , "Korola-Vaji" , "Lal-shak-Vaji" , "Lentil fritters - Dal Puri" , "Lentil soup_Dal" , "Meat Curry_Gosht Bhuna" , "Misti" , "Mixed vegetable stir-fry - Torkari" , "Momo" , "Noodles" , "omelette" , "Parata" , "Payesh-Firni" , "pizza" , "poached_egg" , "Prawn curry - Chingri bhuna" , "Rosogolla" , "Salad" , "Sandwich" , "Shak-Vaji" , "Shawarma" , "Shemai" , "Shik_kabab" , "Singgara" , "Sondesh" , "Tundul ruti - Nan Ruti" , "Vapa Pitha" , "Vegetable fritters - Beguni"};


            Map<Integer, String> foodMap = new HashMap<>();
            foodMap.put(0, "Alu Bhorta");
            foodMap.put(1, "Alu-Vaji");
            foodMap.put(2, "Begun Bhaja");
            foodMap.put(3, "Begun Bhorta");
            foodMap.put(4, "Biriyani");
            foodMap.put(5, "Boiled_egg");
            foodMap.put(6, "Borhani");
            foodMap.put(7, "Burger");
            foodMap.put(8, "Cake");
            foodMap.put(9, "Chicken_Grill");
            foodMap.put(10, "Chicken_curry");
            foodMap.put(11, "Chicken_wings");
            foodMap.put(12, "Chitoi Pitha");
            foodMap.put(13, "Chocolate_cake");
            foodMap.put(14, "Doi");
            foodMap.put(15, "Falooda");
            foodMap.put(16, "Fish Bhuna_Mach Bhuna");
            foodMap.put(17, "French_fries");
            foodMap.put(18, "Fried chicken - Murg Bhaja");
            foodMap.put(19, "Fried fish_Mach Bhaja");
            foodMap.put(20, "Fried_rice");
            foodMap.put(21, "Fuchka");
            foodMap.put(22, "Hilsha_Fish_Curry");
            foodMap.put(23, "Jalebi");
            foodMap.put(24, "Jorda");
            foodMap.put(25, "Kacchi");
            foodMap.put(26, "Khichuri");
            foodMap.put(27, "Korola-Vaji");
            foodMap.put(28, "Lal-shak-Vaji");
            foodMap.put(29, "Lentil fritters - Dal Puri");
            foodMap.put(30, "Lentil soup_Dal");
            foodMap.put(31, "Meat Curry_Gosht Bhuna");
            foodMap.put(32, "Misti");
            foodMap.put(33, "Mixed vegetable stir-fry - Torkari");
            foodMap.put(34, "Momo");
            foodMap.put(35, "Noodles");
            foodMap.put(36, "Parata");
            foodMap.put(37, "Payesh-Firni");
            foodMap.put(38, "Prawn curry - Chingri bhuna");
            foodMap.put(39, "Rosogolla");
            foodMap.put(40, "Salad");
            foodMap.put(41, "Sandwich");
            foodMap.put(42, "Shak-Vaji");
            foodMap.put(43, "Shawarma");
            foodMap.put(44, "Shemai");
            foodMap.put(45, "Shik_kabab");
            foodMap.put(46, "Singgara");
            foodMap.put(47, "Sondesh");
            foodMap.put(48, "Tundul ruti - Nan Ruti");
            foodMap.put(49, "Vapa Pitha");
            foodMap.put(50, "Vegetable fritters - Beguni");
            foodMap.put(51, "cheesecake");
            foodMap.put(52, "cup_cakes");
            foodMap.put(53, "ice_cream");
            foodMap.put(54, "kebab - Gosht Kebab");
            foodMap.put(55, "omelette");
            foodMap.put(56, "pizza");
            foodMap.put(57, "poached_egg");



            result.setText(foodMap.get(maxPos));


            String s = "";
            for(int i = 0; i < foodMap.size(); i++) {
                if (confidences[i] > 0.4 && confidences[i] < 1.0) {
                    s += String.format("%s: %.1f%%\n", foodMap.get(i), confidences[i]*100);
                }

            }
            confidence.setText(s);

            // Releases model resources if no longer used.
            model.close();
        } catch (IOException e) {
            // TODO Handle the exception
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == 3){
                Bitmap image = (Bitmap) data.getExtras().get("data");
                int dimension = Math.min(image.getWidth(), image.getHeight());
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension);
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }else{
                Uri dat = data.getData();
                Bitmap image = null;
                try {
                    image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), dat);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                imageView.setImageBitmap(image);

                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false);
                classifyImage(image);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}