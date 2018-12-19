package soexample.umeng.com.zhujiahua;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(index==0){
                Intent intent = new Intent(MainActivity.this,Main2Activity.class);
                startActivity(intent);
            }
            index--;
            sendEmptyMessageDelayed(0,1000);

        }
    };

    private ImageView img;
    private Button start;
    private int index=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        img = findViewById(R.id.img);
        start = findViewById(R.id.start);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                ObjectAnimator animator1 = ObjectAnimator.ofFloat(img,"alpha",1f,0f,0.8f);
                ObjectAnimator animator2 = ObjectAnimator.ofFloat(img,"rotationY",0,180);
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.play(animator1).with(animator2);
                animatorSet.setDuration(3000);
                animatorSet.start();
                handler.sendEmptyMessage(0);
                break;
        }
    }
}
