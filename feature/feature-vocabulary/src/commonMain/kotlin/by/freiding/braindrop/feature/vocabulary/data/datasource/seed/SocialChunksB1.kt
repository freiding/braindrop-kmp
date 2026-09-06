package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.SOCIAL
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Social life theme. */
internal val SOCIAL_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "make_an_effort",
        text = "make an effort",
        translation = "постараться, приложить усилия",
        headword = "effort",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex(
            "She made an effort to talk to everyone at the party.",
            "Она постаралась поговорить со всеми на вечеринке.",
        ),
        more = listOf(
            ex("Make an effort to keep in touch.", "Постарайся не терять связь."),
            ex("He didn't even make an effort to come.", "Он даже не попытался прийти."),
        ),
        error = err("do an effort", "«Приложить усилия» — make an effort, не «do»."),
        collocations = listOf(
            col("go out of your way", "особо постараться"),
            col("put in the effort", "вложить усилия"),
        ),
    ),
    chunk(
        id = "break_the_news_to_someone",
        text = "break the news to someone",
        translation = "сообщить кому-то новость",
        headword = "news",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N + prep + N",
        example = ex("I had to break the news to my parents.", "Мне пришлось сообщить эту новость родителям."),
        more = listOf(
            ex("Who's going to break the news to her?", "Кто ей об этом скажет?"),
            ex("He broke the news gently.", "Он сообщил новость мягко."),
        ),
        collocations = listOf(
            col("drop a bombshell", "огорошить новостью"),
            col("soften the blow", "смягчить удар"),
        ),
    ),
    chunk(
        id = "give_someone_a_hand",
        text = "give someone a hand",
        translation = "помочь кому-то",
        headword = "hand",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_1000,
        register = INFORMAL,
        pattern = "V + N + N",
        example = ex("Could you give me a hand with these boxes?", "Поможешь мне с этими коробками?"),
        more = listOf(
            ex("She gave me a hand moving the sofa.", "Она помогла мне передвинуть диван."),
            ex("Give us a hand here, would you?", "Подсоби-ка нам тут."),
        ),
        error = err("give a help to someone", "«Помочь» — give someone a hand / help someone."),
        collocations = listOf(
            col("lend a hand", "подсобить"),
            col("pitch in", "включиться, помочь сообща"),
        ),
    ),
    chunk(
        id = "bump_into_someone",
        text = "bump into someone",
        translation = "случайно встретить кого-то",
        headword = "bump",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + prep + N",
        example = ex("I bumped into an old friend at the market.", "На рынке я случайно встретил старого друга."),
        more = listOf(
            ex("We keep bumping into each other lately.", "В последнее время мы постоянно сталкиваемся."),
            ex("Guess who I bumped into today?", "Угадай, кого я сегодня встретил?"),
        ),
        error = err("meet into someone by chance", "«Случайно встретить» — bump into / run into someone."),
        collocations = listOf(
            col("run into someone", "натолкнуться на кого-то"),
            col("cross paths with someone", "пересечься с кем-то"),
        ),
    ),
    chunk(
        id = "tag_along",
        text = "tag along",
        translation = "увязаться за компанию",
        headword = "tag",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv",
        example = ex("Do you mind if I tag along?", "Не против, если я с вами?"),
        more = listOf(
            ex("Her little brother always tags along.", "Её младший брат вечно увязывается следом."),
            ex("You're welcome to tag along.", "Можешь пойти с нами."),
        ),
        collocations = listOf(
            col("come along", "пойти вместе"),
            col("join in", "присоединиться"),
        ),
    ),
    chunk(
        id = "see_eye_to_eye",
        text = "see eye to eye with someone",
        translation = "сходиться во взглядах с кем-то",
        headword = "eye",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + N + prep + N + prep + N",
        example = ex(
            "We don't always see eye to eye, but we respect each other.",
            "Мы не всегда сходимся во взглядах, но уважаем друг друга.",
        ),
        more = listOf(
            ex("They've never seen eye to eye on money.", "В вопросах денег они никогда не сходились."),
            ex("Finally we see eye to eye on this.", "Наконец-то мы в этом согласны."),
        ),
        collocations = listOf(
            col("be on the same page", "быть заодно"),
            col("agree to disagree", "остаться каждый при своём"),
        ),
    ),
    chunk(
        id = "patch_things_up",
        text = "patch things up",
        translation = "помириться, наладить отношения",
        headword = "patch",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N + adv",
        example = ex(
            "They argued but patched things up the next day.",
            "Они поссорились, но на следующий день помирились.",
        ),
        more = listOf(
            ex("It took months to patch things up.", "Наладить отношения удалось лишь спустя месяцы."),
            ex("Can we patch things up over a coffee?", "Может, помиримся за чашкой кофе?"),
        ),
        collocations = listOf(
            col("make up with someone", "помириться с кем-то"),
            col("bury the hatchet", "зарыть топор войны"),
        ),
    ),
    chunk(
        id = "have_a_laugh",
        text = "have a laugh",
        translation = "хорошо посмеяться, весело провести время",
        headword = "laugh",
        theme = SOCIAL,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + N",
        example = ex("We had a good laugh about it afterwards.", "Потом мы над этим от души посмеялись."),
        more = listOf(
            ex("They're always having a laugh in that office.", "В этом офисе всегда весело."),
            ex("It was just a joke — we were having a laugh.", "Это была просто шутка, мы дурачились."),
        ),
        collocations = listOf(
            col("crack a joke", "отпустить шутку"),
            col("for a laugh", "ради смеха"),
        ),
    ),
)
