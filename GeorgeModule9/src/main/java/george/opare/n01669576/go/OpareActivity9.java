// George Opare n01669576
package george.opare.n01669576.go;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.splashscreen.SplashScreen;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import com.google.android.material.navigation.NavigationView;

public class OpareActivity9 extends AppCompatActivity {

    private boolean keepSplash = true;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        splashScreen.setKeepOnScreenCondition(() -> keepSplash);
        new Handler(Looper.getMainLooper()).postDelayed(() -> keepSplash = false, 3000);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.geoToolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.geoDrawerLayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.geo_open_drawer, R.string.geo_close_drawer);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navView = findViewById(R.id.geoNavView);
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.geoNav1) {
                showFragment(new Ge1orgeFragment());
            } else if (id == R.id.geoNav2) {
                showFragment(new Op2areFragment());
            }
            drawerLayout.closeDrawers();
            return true;
        });

        // Ge1orge fragment shows by default after splash
        if (savedInstanceState == null) {
            showFragment(new Ge1orgeFragment());
            navView.setCheckedItem(R.id.geoNav1);
        }
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.geoContainer, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.geo_toolbar_menu, menu);

        // keep sub-menu text and icons visible in both light and dark mode
        SubMenu sub = menu.findItem(R.id.geoMenuOptions).getSubMenu();
        if (sub != null) {
            int color = ContextCompat.getColor(this, R.color.geo_menu_text);
            for (int i = 0; i < sub.size(); i++) {
                MenuItem item = sub.getItem(i);
                SpannableString title = new SpannableString(item.getTitle());
                title.setSpan(new ForegroundColorSpan(color), 0, title.length(), 0);
                item.setTitle(title);
                Drawable icon = item.getIcon();
                if (icon != null) {
                    icon.setTint(color);
                }
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.geoMenuToggle) {
            // dark mode toggle — next commit
            return true;
        } else if (id == R.id.geoMenuSearch) {
            // search dialog — commit after
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}