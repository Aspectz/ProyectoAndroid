package jogasa.simarro.proyectenadal.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import jogasa.simarro.proyectenadal.R;
import jogasa.simarro.proyectenadal.bd.UsuariosOperacional;
import jogasa.simarro.proyectenadal.pojo.Usuario;

public class SignUpActivity extends AppCompatActivity {
    private EditText name,email,firstPassword,secondPassword;
    private Button signUpbutton;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    UsuariosOperacional usuariosOperacional;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        usuariosOperacional = UsuariosOperacional.getInstance(this);

        name = (EditText) findViewById(R.id.nameEditText);
        email = (EditText) findViewById(R.id.emailEditText);
        firstPassword = (EditText) findViewById(R.id.firstPasswordEditText);
        secondPassword = (EditText) findViewById(R.id.secondPasswordEditText);
        signUpbutton = (Button) findViewById(R.id.signUpEditText);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };


        signUpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

        mAuth.addAuthStateListener(authStateListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if(authStateListener!=null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }
    private void signUp(){
        final String username=name.getText().toString();
        final String correo=email.getText().toString();
        final String passwd=firstPassword.getText().toString();
        final String passwd2=secondPassword.getText().toString();


        if(passwd.compareTo(passwd2)!=0){
            Toast.makeText(SignUpActivity.this, "YOUR PASSWORD MUST BE THE SAME", Toast.LENGTH_SHORT).show();
            return;
        }
        if(passwd.length()<6){
            Toast.makeText(this, "YOUR PASSWORD MUST HAVE 6 LETTERS", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(correo,passwd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseUser user=mAuth.getCurrentUser();
                    user.sendEmailVerification();
                    Toast.makeText(SignUpActivity.this, "Email de verificacion enviado", Toast.LENGTH_SHORT).show();
                    Usuario usuario=new Usuario(username,correo,passwd);
                    Log.d("CURRENT","A:"+username+correo);

                    usuariosOperacional.registrarUsuario(usuario);

                    Intent main=new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(main);
                }
            }
        });
    }
}