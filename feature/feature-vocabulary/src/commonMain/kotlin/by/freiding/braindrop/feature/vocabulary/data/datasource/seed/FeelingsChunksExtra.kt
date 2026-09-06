package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.FEELINGS
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Feelings theme. */
internal val FEELINGS_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "be_in_a_bad_mood",
        text = "be in a bad mood",
        translation = "быть в плохом настроении",
        headword = "mood",
        theme = FEELINGS,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "be + prep + adj + N",
        example = ex("Careful — he's in a bad mood today.", "Осторожно — он сегодня не в духе."),
        more = listOf(
            ex("She's been in a bad mood all morning.", "Всё утро она в плохом настроении."),
            ex(
                "Don't take it out on me just because you're in a bad mood.",
                "Не срывайся на мне из-за плохого настроения.",
            ),
        ),
        error = err("have a bad mood", "«В плохом настроении» — be in a bad mood, не «have»."),
        collocations = listOf(
            col("be in a foul mood", "быть в отвратительном настроении"),
            col("brighten up", "повеселеть"),
        ),
    ),
    chunk(
        id = "cheer_someone_up",
        text = "cheer someone up",
        translation = "подбодрить кого-то",
        headword = "up",
        theme = FEELINGS,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N + adv",
        example = ex("I bought her flowers to cheer her up.", "Я купил ей цветы, чтобы поднять настроение."),
        more = listOf(
            ex("Cheer up — it's not the end of the world.", "Не унывай — это не конец света."),
            ex("A walk always cheers me up.", "Прогулка всегда меня подбадривает."),
        ),
        collocations = listOf(
            col("lift someone's spirits", "поднять кому-то дух"),
            col("put a smile on someone's face", "вызвать улыбку"),
        ),
    ),
    chunk(
        id = "be_on_edge",
        text = "be on edge",
        translation = "быть на взводе, нервничать",
        headword = "edge",
        theme = FEELINGS,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + prep + N",
        example = ex(
            "Everyone was on edge before the results came out.",
            "Перед оглашением результатов все были на взводе.",
        ),
        more = listOf(
            ex("Sorry I snapped — I've been on edge all week.", "Извини, что сорвался — я всю неделю на нервах."),
            ex("The noise put me on edge.", "Шум меня раздражал."),
        ),
        collocations = listOf(
            col("be a bundle of nerves", "быть комком нервов"),
            col("calm your nerves", "успокоить нервы"),
        ),
    ),
    chunk(
        id = "get_carried_away",
        text = "get carried away",
        translation = "увлечься, потерять контроль",
        headword = "carried",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + V-ed + adv",
        example = ex(
            "Sorry, I got a bit carried away with the decorations.",
            "Извини, я немного увлёкся с украшениями.",
        ),
        more = listOf(
            ex("Don't get carried away — it's only a game.", "Не заводись — это всего лишь игра."),
            ex("She got carried away and spent far too much.", "Она увлеклась и потратила слишком много."),
        ),
        collocations = listOf(
            col("lose yourself in something", "с головой уйти во что-то"),
            col("go overboard", "перегнуть палку"),
        ),
    ),
    chunk(
        id = "come_to_terms_with",
        text = "come to terms with something",
        translation = "смириться с чем-то",
        headword = "terms",
        theme = FEELINGS,
        level = B2,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N + prep + N",
        example = ex(
            "It took her years to come to terms with the loss.",
            "Ей понадобились годы, чтобы смириться с утратой.",
        ),
        more = listOf(
            ex("He's still coming to terms with the diagnosis.", "Он всё ещё свыкается с диагнозом."),
            ex("You have to come to terms with the fact that it's over.", "Придётся смириться с тем, что всё кончено."),
        ),
        collocations = listOf(
            col("make peace with something", "примириться с чем-то"),
            col("accept the situation", "принять ситуацию"),
        ),
    ),
    chunk(
        id = "be_thrilled_to_bits",
        text = "be thrilled to bits",
        translation = "быть в полном восторге",
        headword = "thrilled",
        theme = FEELINGS,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + adj + idiom",
        example = ex("She was thrilled to bits with the present.", "Она была в полном восторге от подарка."),
        more = listOf(
            ex("We're thrilled to bits for you both.", "Мы безумно рады за вас обоих."),
            ex("He was thrilled to bits to get the part.", "Он был вне себя от радости, что получил роль."),
        ),
        collocations = listOf(
            col("be delighted", "быть в восторге"),
            col("can't stop smiling", "не мочь перестать улыбаться"),
        ),
    ),
    chunk(
        id = "have_mixed_feelings",
        text = "have mixed feelings about something",
        translation = "испытывать смешанные чувства",
        headword = "feelings",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adj + N + prep + N",
        example = ex(
            "I have mixed feelings about moving abroad.",
            "У меня смешанные чувства по поводу переезда за границу.",
        ),
        more = listOf(
            ex("She had mixed feelings when the job ended.", "Когда работа закончилась, у неё были смешанные чувства."),
            ex("People have mixed feelings about the change.", "У людей неоднозначное отношение к переменам."),
        ),
        collocations = listOf(
            col("be in two minds", "не мочь решиться"),
            col("be torn", "разрываться"),
        ),
    ),
    chunk(
        id = "keep_your_cool",
        text = "keep your cool",
        translation = "сохранять спокойствие",
        headword = "cool",
        theme = FEELINGS,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + poss + N",
        example = ex(
            "He kept his cool even when they shouted at him.",
            "Он сохранял спокойствие, даже когда на него кричали.",
        ),
        more = listOf(
            ex("Keep your cool — losing your temper won't help.", "Держи себя в руках — злиться бесполезно."),
            ex("She lost her cool halfway through the interview.", "На середине собеседования она вышла из себя."),
        ),
        collocations = listOf(
            col("lose your cool", "выйти из себя"),
            col("stay level-headed", "оставаться хладнокровным"),
        ),
    ),
    chunk(
        id = "be_over_the_worst",
        text = "be over the worst of it",
        translation = "пережить самое тяжёлое",
        headword = "worst",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "be + prep + N + prep + pron",
        example = ex("She's ill, but she's over the worst of it now.", "Она болеет, но самое тяжёлое уже позади."),
        more = listOf(
            ex("Financially, we're over the worst of it.", "В финансовом плане худшее для нас позади."),
            ex(
                "Once you're over the worst, recovery is quick.",
                "Как только пройдёшь пик, восстановление идёт быстро.",
            ),
        ),
        collocations = listOf(
            col("turn a corner", "пойти на поправку"),
            col("the worst is behind us", "худшее позади"),
        ),
    ),
    chunk(
        id = "not_be_in_the_mood",
        text = "not be in the mood for something",
        translation = "быть не в настроении для чего-то",
        headword = "mood",
        theme = FEELINGS,
        level = A2,
        frequencyBand = TOP_1000,
        register = INFORMAL,
        pattern = "not be + prep + N + prep + N",
        example = ex("I'm not in the mood for a party tonight.", "Сегодня я не в настроении идти на вечеринку."),
        more = listOf(
            ex("She's not in the mood to talk right now.", "Ей сейчас не до разговоров."),
            ex("Sorry, I'm just not in the mood.", "Извини, просто нет настроения."),
        ),
        collocations = listOf(
            col("not feel up to something", "быть не в состоянии что-то делать"),
            col("be in the mood for", "быть настроенным на"),
        ),
    ),
)
