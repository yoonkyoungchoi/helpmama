package kr.hs.emirim.mama;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_up extends AppCompatActivity {
    private static final String TAG = "Sign_up";
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.loginbtn).setOnClickListener(onClickListener);
        findViewById(R.id.gotologin).setOnClickListener(onClickListener);

    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.loginbtn:
                    sign();
                    break;
                case R.id.gotologin:
                    Loginstart();
                    break;
            }
        }
    };

    private void sign() {
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String passwordcheck = ((EditText) findViewById(R.id.passwordcheck)).getText().toString();

        if (email.length() > 0 && password.length() > 0 && passwordcheck.length() > 0) {
            if (password.equals(passwordcheck)) {
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    startToast("회원가입이 되었습니다.");
                                } else {
                                    if (task.getException() != null) {
                                        startToast(task.getException().toString());
                                    }
                                }
                            }
                        });
            } else {
                startToast("비밀번호가 일치하지 않습니다");
                Log.e("비밀번호 에러", "회원가입 비밀번호 에러 확인 바람");
            }
        } else {
            startToast("이메일 또는 비밀번호를 입력해주세요!");
        }
    }
    private void startToast(String msg){
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show();

    }
    private void Loginstart(){
        Intent intent= new Intent(this, Login.class);
        startActivity(intent);
    }
}