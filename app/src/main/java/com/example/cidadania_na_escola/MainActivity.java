package com.example.cidadania_na_escola;

import android.os.Bundle;
import android.view.View;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

/** Contém somente a navegação do protótipo. Não autentica nem persiste dados. */
public class MainActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private BottomNavigationView navigation;

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        navigation = findViewById(R.id.bottom_navigation);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) -> {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars()
                    | WindowInsetsCompat.Type.displayCutout() | WindowInsetsCompat.Type.ime());
            view.setPadding(bars.left, 0, bars.right, bars.bottom);
            View status = findViewById(R.id.status_scrim);
            android.view.ViewGroup.LayoutParams params = status.getLayoutParams();
            params.height = bars.top;
            status.setLayoutParams(params);
            return WindowInsetsCompat.CONSUMED;
        });
        toolbar.setNavigationOnClickListener(v -> getOnBackPressedDispatcher().onBackPressed());
        navigation.setOnItemSelectedListener(item -> {
            String page = "home";
            if (item.getItemId() == R.id.nav_explore) page = "contents";
            else if (item.getItemId() == R.id.nav_quiz) page = "quiz";
            else if (item.getItemId() == R.id.nav_profile) page = "profile";
            open(page);
            return true;
        });
        if (savedInstanceState == null) open("welcome");
    }

    public void open(String page) {
        ScreenFragment current = (ScreenFragment) getSupportFragmentManager()
                .findFragmentById(R.id.screen_container);
        if (current != null && page.equals(current.page())) return;
        androidx.fragment.app.FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction().setReorderingAllowed(true)
                .replace(R.id.screen_container, ScreenFragment.create(page));
        if (current != null) transaction.addToBackStack(null);
        transaction.commit();
    }

    void updateChrome(String page) {
        boolean welcome = page.equals("welcome");
        boolean access = welcome || page.equals("login") || page.equals("register") || page.equals("recover");
        toolbar.setVisibility(welcome || page.equals("login") ? View.GONE : View.VISIBLE);
        navigation.setVisibility(access ? View.GONE : View.VISIBLE);
        toolbar.setTitle(title(page));
        boolean tab = page.equals("home") || page.equals("contents")
                || page.equals("quiz") || page.equals("profile");
        toolbar.setNavigationIcon(tab ? null : androidx.appcompat.content.res.AppCompatResources
                .getDrawable(this, R.drawable.ic_back));
        if (toolbar.getNavigationIcon() != null) toolbar.getNavigationIcon().setTint(getColor(R.color.white));
        toolbar.setLogo(page.equals("home") ? androidx.appcompat.content.res.AppCompatResources
                .getDrawable(this, R.drawable.brand_small) : null);
        int selected = R.id.nav_home;
        if (page.equals("contents") || page.startsWith("topic:") || page.equals("materials")) selected = R.id.nav_explore;
        else if (page.equals("quiz")) selected = R.id.nav_quiz;
        else if (page.equals("profile") || page.equals("saved") || page.equals("likes")
                || page.equals("comments") || page.equals("settings")) selected = R.id.nav_profile;
        navigation.getMenu().findItem(selected).setChecked(true);
        findViewById(R.id.main).setBackgroundColor(getColor(welcome ? R.color.navy : R.color.surface));
        androidx.core.view.WindowInsetsControllerCompat controller =
                new androidx.core.view.WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        controller.setAppearanceLightStatusBars(false);
        controller.setAppearanceLightNavigationBars(!welcome);
    }

    private String title(String page) {
        if (page.startsWith("topic:")) return "Conteúdo educativo";
        switch (page) {
            case "contents": return "Conteúdos";
            case "quiz": return "Quiz";
            case "profile": return "Perfil";
            case "register": return "Criar conta";
            case "recover": return "Recuperar senha";
            case "posts": return "Publicações";
            case "publish": return "Nova publicação";
            case "materials": return "Materiais educativos";
            case "about": return "Sobre o projeto";
            case "settings": return "Configurações";
            case "saved": return "Meus conteúdos";
            case "likes": return "Curtidas";
            case "comments": return "Comentários";
            default: return "Cidadania na Escola";
        }
    }
}
