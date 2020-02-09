package com.example.firebase02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseUser;

public class SegundaActivity extends AppCompatActivity {


    private Button myLogout;

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView uidTextView;
    private CircleImageView imageView;

    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        myLogout = (Button)findViewById(R.id.logout);

        nameTextView = (TextView) findViewById(R.id.nameTextView);
        emailTextView = (TextView) findViewById(R.id.emailTextView);
        //uidTextView = (TextView) findViewById(R.id.uidTextView);
        imageView = (CircleImageView) findViewById(R.id.my_image_View);

        /*if (AccessToken.getCurrentAccessToken() == null) {
            Toast.makeText(this,"Token==NULL",Toast.LENGTH_LONG).show();
        }*/

        //Usuario de Firebase
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        //Google
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //---------------------------------------------------------

        if (user != null) {
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();
            //String uid = user.getUid();

            if(name == null){
                nameTextView.setText("notNameUser");
            }
            else {
                nameTextView.setText(name);
            }
            emailTextView.setText(email);
            //uidTextView.setText(uid);


            if(String.valueOf(photoUrl) != null){
                //Toast.makeText(this,String.valueOf(photoUrl),Toast.LENGTH_LONG).show();
                Glide.with(this).load(String.valueOf(photoUrl)).into(imageView);
            }
            Toast.makeText(this,String.valueOf(photoUrl),Toast.LENGTH_LONG).show();


        }

        //Desconectarse de la actividad principal
        myLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Facebook
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
                //Google y correo electronico
                else{
                    FirebaseAuth.getInstance().signOut();
                    signOut();
                }
            }
        });
    }


    //Ir a la actividad del login
    private void goLoginScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

   //Desconectarse de Google
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        goLoginScreen();
                    }
                });
    }
}
