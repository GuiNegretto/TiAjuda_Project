package com.br.ucs.tiajudaandroid.activities;
import android.app.Activity;
import android.content.*;
import android.os.*;
import android.widget.*;
import com.br.ucs.tiajudaandroid.R;
public class LoginActivity extends Activity{
    protected void onCreate(Bundle b){
        super.onCreate(b);setContentView(R.layout.activity_login);
        EditText email=findViewById(R.id.emailLogin); 
        EditText senha=findViewById(R.id.senhaLogin);
        Button entrar=findViewById(R.id.btnEntrar); 
        TextView link=findViewById(R.id.linkCadastro);
        entrar.setOnClickListener(v->{
            startActivity(new Intent(this,MainActivity.class));
        });
        link.setOnClickListener(v->{
            startActivity(new Intent(this,CadastroActivity.class));
        });
    }
}
