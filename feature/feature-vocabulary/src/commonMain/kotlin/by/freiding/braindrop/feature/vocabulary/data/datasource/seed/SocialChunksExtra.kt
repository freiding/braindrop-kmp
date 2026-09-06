package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.SOCIAL
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Social life theme. */
internal val SOCIAL_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "make_friends",
        text = "make friends",
        translation = "заводить друзей",
        headword = "friends",
        theme = SOCIAL,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("She makes friends easily wherever she goes.", "Она легко заводит друзей, куда бы ни приехала."),
        more = listOf(
            ex("It's hard to make friends as an adult.", "Во взрослом возрасте трудно заводить друзей."),
            ex("They made friends on the first day of the course.", "Они подружились в первый день курса."),
        ),
        error = err("do friends", "«Заводить друзей» — make friends, не «do»."),
        collocations = listOf(
            col("get to know someone", "познакомиться поближе"),
            col("an old friend", "давний друг"),
        ),
    ),
    chunk(
        id = "grab_a_coffee",
        text = "grab a coffee",
        translation = "выпить кофе (по-быстрому)",
        headword = "coffee",
        theme = SOCIAL,
        level = A2,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + N",
        example = ex("Do you want to grab a coffee after this?", "Не хочешь после этого выпить кофе?"),
        more = listOf(
            ex("We grabbed a quick coffee between meetings.", "Между встречами мы по-быстрому выпили кофе."),
            ex("Let's grab a coffee and catch up.", "Давай выпьем кофе и поболтаем."),
        ),
        collocations = listOf(
            col("go for a drink", "сходить выпить"),
            col("get a bite to eat", "перекусить"),
        ),
    ),
    chunk(
        id = "have_someone_over_for_dinner",
        text = "have someone over for dinner",
        translation = "пригласить кого-то на ужин",
        headword = "dinner",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N + adv + prep + N",
        example = ex("We're having the neighbours over for dinner.", "Мы зовём соседей на ужин."),
        more = listOf(
            ex("They had us over for dinner last week.", "На прошлой неделе они позвали нас на ужин."),
            ex("Have them over — I'll cook.", "Позови их — я приготовлю."),
        ),
        collocations = listOf(
            col("bring a bottle", "прийти с бутылкой вина"),
            col("a dinner party", "званый ужин"),
        ),
    ),
    chunk(
        id = "be_the_life_and_soul",
        text = "be the life and soul of the party",
        translation = "быть душой компании",
        headword = "soul",
        theme = SOCIAL,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + N + idiom",
        example = ex("Her brother is always the life and soul of the party.", "Её брат всегда душа компании."),
        more = listOf(
            ex(
                "He's quiet at work but the life and soul at parties.",
                "На работе он тихий, а на вечеринках — душа компании.",
            ),
            ex(
                "You don't have to be the life and soul to enjoy yourself.",
                "Не обязательно быть заводилой, чтобы хорошо провести время.",
            ),
        ),
        collocations = listOf(
            col("a wallflower", "тот, кто стесняется на вечеринках"),
            col("work the room", "общаться со всеми на мероприятии"),
        ),
    ),
    chunk(
        id = "keep_someone_company",
        text = "keep someone company",
        translation = "составить кому-то компанию",
        headword = "company",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N + N",
        example = ex("I'll keep you company while you wait.", "Я составлю тебе компанию, пока ты ждёшь."),
        more = listOf(
            ex("The dog keeps her company during the day.", "Днём собака составляет ей компанию."),
            ex("Stay and keep me company for a bit.", "Останься, побудь со мной немного."),
        ),
        error = err("make someone company", "«Составить компанию» — keep someone company, не «make»."),
        collocations = listOf(
            col("enjoy someone's company", "наслаждаться чьим-то обществом"),
            col("good company", "приятный собеседник"),
        ),
    ),
    chunk(
        id = "lose_touch_with_someone",
        text = "lose touch with someone",
        translation = "потерять связь с кем-то",
        headword = "touch",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N + prep + N",
        example = ex("We lost touch after university.", "После университета мы потеряли связь."),
        more = listOf(
            ex("I don't want to lose touch with you.", "Я не хочу терять с тобой связь."),
            ex(
                "They lost touch for years, then reconnected online.",
                "Они годами не общались, а потом нашлись в интернете.",
            ),
        ),
        collocations = listOf(
            col("get back in touch", "снова связаться"),
            col("an old contact", "старый знакомый"),
        ),
    ),
    chunk(
        id = "have_a_word_with_someone",
        text = "have a word with someone",
        translation = "поговорить с кем-то (наедине)",
        headword = "word",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + N + prep + N",
        example = ex("Can I have a word with you in private?", "Можно поговорить с тобой наедине?"),
        more = listOf(
            ex("The manager had a word with him about being late.", "Менеджер поговорил с ним об опозданиях."),
            ex("I'll have a word and sort it out.", "Я переговорю и всё улажу."),
        ),
        collocations = listOf(
            col("pull someone aside", "отвести кого-то в сторону"),
            col("clear the air", "прояснить отношения"),
        ),
    ),
    chunk(
        id = "put_someone_up_for_the_night",
        text = "put someone up for the night",
        translation = "приютить кого-то на ночь",
        headword = "up",
        theme = SOCIAL,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N + adv + prep + N",
        example = ex(
            "They put us up for the night when our flight was cancelled.",
            "Они приютили нас на ночь, когда отменили наш рейс.",
        ),
        more = listOf(
            ex("Can you put me up for a couple of nights?", "Можешь приютить меня на пару ночей?"),
            ex("We put friends up all the time.", "Мы постоянно принимаем друзей с ночёвкой."),
        ),
        collocations = listOf(
            col("crash at someone's place", "переночевать у кого-то"),
            col("the spare room", "гостевая комната"),
        ),
    ),
    chunk(
        id = "get_off_on_the_wrong_foot",
        text = "get off on the wrong foot",
        translation = "неудачно начать общение",
        headword = "foot",
        theme = SOCIAL,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + prep + adj + N",
        example = ex(
            "We got off on the wrong foot, but we're fine now.",
            "Поначалу мы не поладили, но сейчас всё хорошо.",
        ),
        more = listOf(
            ex(
                "I think I got off on the wrong foot with my new boss.",
                "Кажется, я неудачно начал с новым начальником.",
            ),
            ex("Don't get off on the wrong foot — apologise now.", "Не начинай с конфликта — извинись сразу."),
        ),
        collocations = listOf(
            col("make a good first impression", "произвести хорошее первое впечатление"),
            col("start afresh", "начать с чистого листа"),
        ),
    ),
    chunk(
        id = "drift_apart",
        text = "drift apart",
        translation = "отдалиться друг от друга",
        headword = "drift",
        theme = SOCIAL,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + adv",
        example = ex(
            "Old friends can just drift apart over time.",
            "Со временем старые друзья могут просто отдалиться.",
        ),
        more = listOf(
            ex("They drifted apart after she moved away.", "После её переезда они отдалились."),
            ex("We've drifted apart, but there's no bad feeling.", "Мы отдалились, но обид нет."),
        ),
        collocations = listOf(
            col("grow apart", "разойтись со временем"),
            col("stay close", "оставаться близкими"),
        ),
    ),
)
