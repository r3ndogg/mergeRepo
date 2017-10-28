package au.edu.federation.myapplication.DietaryLogPackage;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.federation.myapplication.GlobalClasses.GlobalOld;
import au.edu.federation.myapplication.R;

public class AddDietaryLog extends AppCompatActivity implements AdapterView.OnItemClickListener {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    ImageView mImageView;
    String mCurrentPhotoPath;
    Uri photoURI;
    GlobalOld globalOld;
    Spinner spinner;
    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dietary_log);

        globalOld = globalOld.getInstance();
        mImageView = (ImageView) findViewById(R.id.mImageView);

        spinner = (Spinner) findViewById(R.id.spinnerCategory);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dietaryCategories, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            Bundle extras = data.getExtras();
//            Bitmap imageBitmap = (Bitmap) extras.get("data");
//            mImageView.setImageBitmap(imageBitmap);

            Uri uri = photoURI;
//            Bitmap myImg = BitmapFactory.decodeFile(uri.getPath());
            Bitmap myImg;
            myImg = createBitmapFromUri(photoURI);
            mImageView.setImageBitmap(myImg);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Resources res = getResources();
        String[] categoryArray = res.getStringArray(R.array.dietaryCategories);
        category = categoryArray[position];

    }

    public void onNothingSelected(AdapterView<?> parent) {
        //Add a not selected thing.
    }

    public void TakePhoto(View v){
        dispatchTakePictureIntent();
    }

    public void LogEntry(View v){
        EditText mText = (EditText)findViewById(R.id.foodName);

        String logName = mText.getText().toString();
        String timeStamp = new SimpleDateFormat("HH:mm:ss_dd:MM:yyyy").format(new Date());
        Bitmap foodPhoto = createBitmapFromUri(photoURI);

        DietaryLogItem newItem = new DietaryLogItem(foodPhoto, logName, timeStamp, photoURI, category);

        LogEntry(newItem);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                Log.d("CAP", "dispatchTakePictureIntent: Creating File");
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Log.d("CAP", "dispatchTakePictureIntent:" + photoFile.toString());
                photoURI = FileProvider.getUriForFile(this,"com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "DietaryLog";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = new File(storageDir, imageFileName);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        Log.d("CAP", "File name" + imageFileName);

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private boolean LogEntry(DietaryLogItem newItem){

        if ( globalOld.dietaryLogList.add(newItem)) {
            Toast.makeText(AddDietaryLog.this, "Food Added to Log!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DietaryLog.class);
            startActivity(intent);
            finish();
            return true;
        }
        else {
            return false;
        }
    }

    private Bitmap createBitmapFromUri(Uri imageUri){
        Bitmap myImg = null;
        try {
            myImg = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoURI);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myImg;
    }



//TODO: Save files and add data


}
