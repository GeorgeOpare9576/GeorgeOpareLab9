// George Opare n01669576
package george.opare.n01669576.go;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

public class OpareActivity9 extends AppCompatActivity {

    private boolean keepSplash = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> keepSplash);

        new Handler(Looper.getMainLooper()).postDelayed(
                () -> keepSplash = false, 3000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}