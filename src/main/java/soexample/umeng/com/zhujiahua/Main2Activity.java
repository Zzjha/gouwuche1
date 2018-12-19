package soexample.umeng.com.zhujiahua;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import soexample.umeng.com.zhujiahua.fragment.SyFragment;
import soexample.umeng.com.zhujiahua.fragment.WdFragment;

public class Main2Activity extends AppCompatActivity {

    private RadioButton sy;
    private RadioButton wd;
    private RadioGroup rg;
    private FrameLayout frame;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
        manager.beginTransaction().replace(R.id.frame,new SyFragment()).commit();

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.sy:
                        manager.beginTransaction().replace(R.id.frame,new SyFragment()).commit();
                        break;
                    case R.id.wd:
                        manager.beginTransaction().replace(R.id.frame,new WdFragment()).commit();
                        break;
                }
            }
        });
    }

    private void initView() {
        sy = findViewById(R.id.sy);
        wd = findViewById(R.id.wd);
        rg = findViewById(R.id.rg);
        frame = findViewById(R.id.frame);
        manager = getSupportFragmentManager();
    }
}
