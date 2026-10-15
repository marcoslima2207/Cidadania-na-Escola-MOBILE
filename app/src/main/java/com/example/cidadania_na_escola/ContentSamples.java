package com.example.cidadania_na_escola;

/** Textos locais para composição das telas. Substituir quando a API for integrada. */
final class ContentSamples {
    static final String[] TITLES = {
            "O que é cidadania?", "Direitos trabalhistas no Brasil",
            "Combate à desinformação", "Democracia e participação", "Antes de compartilhar, confira"
    };
    static final String[] CATEGORIES = {"CIDADANIA", "DIREITOS TRABALHISTAS", "DESINFORMAÇÃO", "DEMOCRACIA", "CHECAGEM"};
    static final int[] ICONS = {R.drawable.ic_person, R.drawable.ic_balance,
            R.drawable.ic_shield, R.drawable.ic_democracy, R.drawable.ic_search};
    static final int[] COLORS = {R.color.pale_blue, R.color.pale_gold,
            R.color.pale_green, R.color.pale_blue, R.color.pale_green};
    static final String[] SUMMARIES = {
            "A cidadania faz parte da nossa vida em comunidade. Ela envolve direitos, deveres e participação nas decisões que afetam a sociedade.",
            "Conhecer os direitos relacionados ao trabalho ajuda a compreender situações do cotidiano e a buscar informação em fontes confiáveis.",
            "Nem tudo o que circula nas redes é confiável. Desenvolver o pensamento crítico ajuda a reconhecer conteúdos enganosos.",
            "A democracia se fortalece com o diálogo, o respeito às diferenças e a participação das pessoas na vida pública.",
            "Ao receber uma informação, reserve um momento para observar a fonte, a data e o contexto antes de compartilhar."
    };
    static final String[][] POINTS = {
            {"Direitos e deveres", "Participação na comunidade", "Respeito às diferenças"},
            {"Informação para o dia a dia", "Fontes oficiais e confiáveis", "Conhecimento e orientação"},
            {"Leia além do título", "Observe quem publicou", "Compare diferentes fontes"},
            {"Participação popular", "Diálogo e escuta", "Decisões coletivas"},
            {"Confira a origem", "Observe a data e o contexto", "Procure outras fontes"}
    };
    private ContentSamples() { }
}
