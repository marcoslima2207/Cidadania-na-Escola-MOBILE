package com.example.cidadania_na_escola;

import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

/** Telas estáticas: somente os links de navegação têm listeners. */
public class ScreenFragment extends Fragment {
    public static ScreenFragment create(String page) {
        ScreenFragment fragment = new ScreenFragment();
        Bundle arguments = new Bundle();
        arguments.putString("page", page);
        fragment.setArguments(arguments);
        return fragment;
    }

    String page() { return requireArguments().getString("page", "welcome"); }

    @Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle state) {
        int layout;
        switch (page()) {
            case "welcome": layout = R.layout.screen_welcome; break;
            case "login": layout = R.layout.screen_login; break;
            case "home": layout = R.layout.screen_home; break;
            case "contents": layout = R.layout.screen_contents; break;
            case "quiz": layout = R.layout.screen_quiz; break;
            case "profile": layout = R.layout.screen_profile; break;
            default: layout = R.layout.screen_generic;
        }
        return inflater.inflate(layout, container, false);
    }

    @Override public void onViewCreated(@NonNull View view, @Nullable Bundle state) {
        switch (page()) {
            case "welcome":
                link(view, R.id.welcome_login, "login");
                link(view, R.id.welcome_register, "register");
                link(view, R.id.welcome_explore, "home");
                break;
            case "login":
                link(view, R.id.login_register, "register");
                link(view, R.id.login_forgot, "recover");
                link(view, R.id.login_preview, "home");
                break;
            case "home":
                link(view, R.id.home_contents, "contents");
                link(view, R.id.home_quiz, "quiz");
                link(view, R.id.home_posts, "posts");
                link(view, R.id.home_profile, "profile");
                link(view, R.id.home_featured, "topic:1");
                link(view, R.id.home_all, "contents");
                link(view, R.id.home_about, "about");
                break;
            case "contents":
                LinearLayout list = view.findViewById(R.id.content_list);
                for (int i = 0; i < ContentSamples.TITLES.length; i++) contentCard(list, i);
                // A busca e os chips compõem a tela, sem executar filtros.
                view.findViewById(R.id.search_input).setEnabled(false);
                link(view, R.id.contents_materials, "materials");
                break;
            case "profile": profile(view.findViewById(R.id.profile_options)); break;
            case "quiz": break;
            default: generic(view.findViewById(R.id.generic_content));
        }
    }

    @Override public void onResume() {
        super.onResume();
        ((MainActivity) requireActivity()).updateChrome(page());
    }

    private void open(String target) { ((MainActivity) requireActivity()).open(target); }
    private void link(View root, int id, String target) {
        root.findViewById(id).setOnClickListener(v -> open(target));
    }
    private int dp(int value) { return Math.round(value * getResources().getDisplayMetrics().density); }
    private int color(int id) { return requireContext().getColor(id); }

    private LinearLayout column() {
        LinearLayout layout = new LinearLayout(requireContext());
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return layout;
    }
    private TextView label(String value, int size, int color, boolean bold) {
        TextView label = new TextView(requireContext());
        label.setText(value);
        label.setTextSize(size);
        label.setTextColor(color(color));
        label.setTypeface(Typeface.create(bold ? "sans-serif-condensed" : "sans-serif", bold ? Typeface.BOLD : Typeface.NORMAL));
        label.setLineSpacing(dp(3), 1);
        label.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        return label;
    }
    private void heading(LinearLayout parent, String title, String description) {
        parent.addView(label(title, 27, R.color.ink, true));
        TextView subtitle = label(description, 15, R.color.muted, false);
        subtitle.setPadding(0, dp(10), 0, dp(24));
        parent.addView(subtitle);
    }
    private void section(LinearLayout parent, String text) {
        TextView heading = label(text, 20, R.color.ink, true);
        heading.setPadding(0, dp(22), 0, dp(12));
        parent.addView(heading);
    }
    private ImageView icon(int resource, int size) {
        ImageView icon = new ImageView(requireContext());
        icon.setImageResource(resource);
        icon.setLayoutParams(new LinearLayout.LayoutParams(dp(size), dp(size)));
        icon.setImportantForAccessibility(View.IMPORTANT_FOR_ACCESSIBILITY_NO);
        return icon;
    }
    private LinearLayout box(LinearLayout parent) {
        LinearLayout box = column();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.bottomMargin = dp(12);
        box.setLayoutParams(params);
        box.setPadding(dp(16), dp(18), dp(16), dp(18));
        box.setBackgroundResource(R.drawable.bg_card);
        parent.addView(box);
        return box;
    }
    private void clickable(View view, String destination) {
        view.setOnClickListener(v -> open(destination));
        android.util.TypedValue value = new android.util.TypedValue();
        requireContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, value, true);
        view.setForeground(androidx.appcompat.content.res.AppCompatResources.getDrawable(requireContext(), value.resourceId));
    }
    private void contentCard(LinearLayout parent, int index) {
        LinearLayout card = box(parent);
        card.setOrientation(LinearLayout.HORIZONTAL);
        card.setGravity(Gravity.CENTER_VERTICAL);
        card.setPadding(dp(12), dp(12), dp(12), dp(12));
        ImageView image = icon(ContentSamples.ICONS[index], 48);
        image.setPadding(dp(13), dp(13), dp(13), dp(13));
        image.setBackgroundTintList(ColorStateList.valueOf(color(ContentSamples.COLORS[index])));
        image.setBackgroundResource(R.drawable.bg_tile_blue);
        card.addView(image);
        LinearLayout copy = column();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1);
        params.setMarginStart(dp(14));
        params.setMarginEnd(dp(8));
        copy.setLayoutParams(params);
        copy.addView(label(ContentSamples.TITLES[index], 18, R.color.ink, true));
        copy.addView(label("3 min de leitura", 12, R.color.muted, false));
        card.addView(copy);
        card.addView(icon(R.drawable.ic_arrow, 18));
        clickable(card, "topic:" + index);
    }
    private void option(LinearLayout parent, String text, int drawable, String destination) {
        LinearLayout row = new LinearLayout(requireContext());
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER_VERTICAL);
        row.setPadding(dp(6), dp(14), dp(6), dp(14));
        row.setMinimumHeight(dp(52));
        row.addView(icon(drawable, 23));
        TextView title = label(text, 16, text.equals("Sair") ? R.color.red : R.color.ink, true);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, -2, 1);
        params.setMarginStart(dp(16));
        title.setLayoutParams(params);
        row.addView(title);
        row.addView(icon(R.drawable.ic_arrow, 18));
        parent.addView(row);
        View divider = new View(requireContext());
        divider.setBackgroundColor(color(R.color.outline));
        parent.addView(divider, new LinearLayout.LayoutParams(-1, dp(1)));
        clickable(row, destination);
    }
    private void profile(LinearLayout parent) {
        option(parent, "Meus conteúdos", R.drawable.ic_book, "saved");
        option(parent, "Curtidas", R.drawable.ic_heart, "likes");
        option(parent, "Comentários", R.drawable.ic_comment, "comments");
        option(parent, "Configurações", R.drawable.ic_settings, "settings");
        option(parent, "Sair", R.drawable.ic_logout, "welcome");
    }
    private void button(LinearLayout parent, String text, String destination) {
        MaterialButton button = new MaterialButton(requireContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, dp(56));
        params.topMargin = dp(12);
        button.setLayoutParams(params);
        button.setText(text);
        button.setTextSize(16);
        button.setTextColor(color(R.color.white));
        button.setBackgroundTintList(ColorStateList.valueOf(color(R.color.green)));
        button.setCornerRadius(dp(12));
        button.setInsetTop(0);
        button.setInsetBottom(0);
        if (destination == null) button.setEnabled(false);
        else button.setOnClickListener(v -> open(destination));
        parent.addView(button);
    }
    private void field(LinearLayout parent, String hint, int inputType, boolean multiline) {
        TextInputLayout input = new TextInputLayout(requireContext(), null, com.google.android.material.R.attr.textInputOutlinedStyle);
        input.setHint(hint);
        input.setBoxCornerRadii(dp(12), dp(12), dp(12), dp(12));
        TextInputEditText edit = new TextInputEditText(input.getContext());
        edit.setInputType(inputType);
        edit.setTextSize(15);
        edit.setMinHeight(dp(56));
        if (multiline) { edit.setMinLines(5); edit.setGravity(Gravity.TOP); }
        input.addView(edit, new LinearLayout.LayoutParams(-1, -2));
        if (inputType == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
            input.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
        }
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-1, -2);
        params.bottomMargin = dp(16);
        parent.addView(input, params);
    }
    private void note(LinearLayout parent, String text) {
        TextView note = label(text, 12, R.color.muted, false);
        note.setPadding(0, dp(18), 0, dp(8));
        parent.addView(note);
    }

    private void generic(LinearLayout parent) {
        if (page().startsWith("topic:")) {
            int index = Integer.parseInt(page().substring(6));
            article(parent, index);
            return;
        }
        switch (page()) {
            case "register":
                heading(parent, "Comece sua jornada", "Um espaço para aprender e participar.");
                field(parent, "Nome completo", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME, false);
                field(parent, "Nome de usuário", InputType.TYPE_CLASS_TEXT, false);
                field(parent, "E-mail", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, false);
                field(parent, "Senha", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, false);
                field(parent, "Confirmar senha", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD, false);
                button(parent, "Criar conta", null);
                button(parent, "Já tenho uma conta", "login");
                note(parent, "Prévia do formulário. Nenhuma conta será criada.");
                break;
            case "recover":
                heading(parent, "Esqueceu sua senha?", "Informe o e-mail associado à sua conta.");
                field(parent, "E-mail", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS, false);
                button(parent, "Enviar instruções", null);
                button(parent, "Voltar para entrar", "login");
                note(parent, "Prévia do formulário. Nenhum e-mail será enviado.");
                break;
            case "posts":
                heading(parent, "Nossa comunidade", "Ideias e descobertas que aproximam.");
                button(parent, "＋  Nova publicação", "publish");
                section(parent, "Publicações recentes");
                post(parent, "Ana Souza", "CIDADANIA", "Aprender sobre cidadania também é olhar para a nossa comunidade. Que mudanças você gostaria de ver na sua escola?", "12 curtidas   •   3 comentários");
                post(parent, "João Silva", "EDUCAÇÃO", "Hoje a conversa foi sobre verificar as informações antes de compartilhar. Uma pequena atitude faz diferença!", "8 curtidas   •   2 comentários");
                note(parent, "Publicações e contagens ilustrativas.");
                break;
            case "publish":
                heading(parent, "Compartilhe uma ideia", "Sua voz também faz parte dessa conversa.");
                field(parent, "Título da publicação", InputType.TYPE_CLASS_TEXT, false);
                field(parent, "Assunto", InputType.TYPE_CLASS_TEXT, false);
                field(parent, "O que você gostaria de compartilhar?", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE, true);
                button(parent, "Adicionar foto ou vídeo", null);
                button(parent, "Publicar", null);
                note(parent, "Prévia do formulário. O conteúdo não será publicado nem salvo.");
                break;
            case "materials":
                heading(parent, "Para continuar aprendendo", "Materiais de apoio para estudar no seu ritmo.");
                material(parent, "Guia de cidadania", "Direitos, deveres e participação.");
                material(parent, "Informação com responsabilidade", "Um roteiro para conferir informações.");
                material(parent, "Trabalho e cidadania", "Temas para conversar em sala de aula.");
                note(parent, "Exemplos visuais. Os arquivos serão disponibilizados futuramente.");
                break;
            case "about":
                heading(parent, "Cidadania na Escola", "Informação de qualidade. Cidadania mais forte.");
                parent.addView(icon(R.drawable.brand_mark, 88));
                section(parent, "Educação que aproxima");
                parent.addView(label("Um projeto de extensão universitária que aproxima a comunidade escolar de temas como cidadania, democracia, combate à desinformação e direitos trabalhistas.", 16, R.color.ink, false));
                section(parent, "Para quem é o projeto?");
                parent.addView(label("Para estudantes, educadores e toda a comunidade, com atenção à Educação de Jovens e Adultos (EJA).", 15, R.color.muted, false));
                section(parent, "Nossa proposta");
                parent.addView(label("Transformar assuntos complexos em conteúdos acessíveis, incentivar o pensamento crítico e fortalecer a participação cidadã.", 15, R.color.muted, false));
                note(parent, "Cidadania hoje, por um amanhã melhor.");
                break;
            case "saved":
            case "likes":
                heading(parent, page().equals("saved") ? "Meus conteúdos" : "Conteúdos curtidos", "Uma prévia da sua coleção de aprendizado.");
                contentCard(parent, 0); contentCard(parent, 1);
                note(parent, "Coleção ilustrativa. Nenhum conteúdo foi salvo ou curtido.");
                break;
            case "comments":
                heading(parent, "Suas conversas", "Um espaço para trocar conhecimentos.");
                post(parent, "João Silva", "EM: O QUE É CIDADANIA?", "Um tema muito importante para conversar na escola!", "Comentário ilustrativo");
                break;
            case "settings":
                heading(parent, "Do seu jeito", "Preferências da sua experiência.");
                section(parent, "Conta");
                field(parent, "Nome", InputType.TYPE_CLASS_TEXT, false);
                field(parent, "Biografia", InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE, true);
                button(parent, "Salvar alterações", null);
                section(parent, "Preferências");
                SwitchMaterial notifications = new SwitchMaterial(requireContext());
                notifications.setText(R.string.receive_notifications);
                notifications.setTextColor(color(R.color.ink));
                notifications.setEnabled(false);
                parent.addView(notifications);
                note(parent, "Prévia das configurações. Nenhuma preferência será salva.");
                break;
            default: throw new IllegalArgumentException("Tela desconhecida: " + page());
        }
    }
    private void article(LinearLayout parent, int index) {
        TextView category = label(ContentSamples.CATEGORIES[index] + "  •  3 MIN", 12, R.color.green_dark, true);
        category.setPadding(0, 0, 0, dp(14));
        parent.addView(category);
        heading(parent, ContentSamples.TITLES[index], ContentSamples.SUMMARIES[index]);
        ImageView illustration = icon(R.drawable.illustration_learning, 160);
        illustration.setLayoutParams(new LinearLayout.LayoutParams(-1, dp(160)));
        illustration.setScaleType(ImageView.ScaleType.CENTER_CROP);
        parent.addView(illustration);
        section(parent, "Para refletir");
        for (int i = 0; i < ContentSamples.POINTS[index].length; i++) {
            LinearLayout item = box(parent);
            item.addView(label("0" + (i + 1) + "  " + ContentSamples.POINTS[index][i], 18, R.color.ink, true));
        }
        note(parent, "Conteúdo ilustrativo para apresentação da tela.");
        button(parent, "Explorar outros conteúdos", "contents");
    }
    private void post(LinearLayout parent, String name, String category, String body, String counts) {
        LinearLayout post = box(parent);
        LinearLayout author = new LinearLayout(requireContext());
        author.setGravity(Gravity.CENTER_VERTICAL);
        author.addView(icon(R.drawable.avatar_student, 40));
        TextView title = label(name, 17, R.color.ink, true);
        title.setPadding(dp(12), 0, 0, 0);
        author.addView(title);
        post.addView(author);
        TextView tag = label(category, 11, R.color.green_dark, true);
        tag.setPadding(0, dp(16), 0, dp(10));
        post.addView(tag);
        post.addView(label(body, 15, R.color.ink, false));
        note(post, counts);
    }
    private void material(LinearLayout parent, String title, String description) {
        LinearLayout card = box(parent);
        card.addView(icon(R.drawable.ic_book, 32));
        section(card, title);
        card.addView(label(description, 15, R.color.muted, false));
        button(card, "Visualizar material", null);
    }
}
