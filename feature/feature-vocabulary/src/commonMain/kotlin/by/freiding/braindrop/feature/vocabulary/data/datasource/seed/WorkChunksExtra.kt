package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.FORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.WORK
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Work theme. */
internal val WORK_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "meet_a_client",
        text = "meet a client",
        translation = "встречаться с клиентом",
        headword = "client",
        theme = WORK,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I'm meeting a client at ten this morning.", "Сегодня в десять у меня встреча с клиентом."),
        more = listOf(
            ex("She meets clients face to face whenever she can.", "Она по возможности встречается с клиентами лично."),
            ex("We met the client to go over the contract.", "Мы встретились с клиентом, чтобы обсудить договор."),
        ),
        collocations = listOf(
            col("win a client", "привлечь клиента"),
            col("lose a client", "потерять клиента"),
            col("a demanding client", "требовательный клиент"),
        ),
    ),
    chunk(
        id = "chair_a_meeting",
        text = "chair a meeting",
        translation = "вести совещание",
        headword = "chair",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_5000,
        register = FORMAL,
        pattern = "V + N",
        example = ex(
            "She chaired the meeting in the director's absence.",
            "Она вела совещание в отсутствие директора.",
        ),
        more = listOf(
            ex("Who's chairing today's meeting?", "Кто сегодня ведёт совещание?"),
            ex("He chairs the finance committee.", "Он председательствует в финансовом комитете."),
        ),
        collocations = listOf(
            col("take the minutes", "вести протокол"),
            col("open the meeting", "открыть совещание"),
        ),
        forms = listOf(verb("chair"), noun("chairperson")),
    ),
    chunk(
        id = "hit_a_deadline",
        text = "work to a deadline",
        translation = "работать в условиях жёстких сроков",
        headword = "deadline",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("I don't work well to a deadline.", "Я плохо работаю, когда поджимают сроки."),
        more = listOf(
            ex("The whole team is working to a tight deadline.", "Вся команда работает в условиях жёсткого дедлайна."),
            ex("We're up against a deadline this week.", "На этой неделе нас поджимает срок."),
        ),
        collocations = listOf(
            col("be up against a deadline", "поджимает срок"),
            col("push back a deadline", "перенести срок"),
        ),
    ),
    chunk(
        id = "take_minutes",
        text = "take the minutes",
        translation = "вести протокол собрания",
        headword = "minutes",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_5000,
        register = FORMAL,
        pattern = "V + N",
        example = ex("Could you take the minutes today?", "Ты не мог бы сегодня вести протокол?"),
        more = listOf(
            ex("The minutes of the last meeting were approved.", "Протокол прошлого собрания был утверждён."),
            ex("She takes the minutes and circulates them afterwards.", "Она ведёт протокол и потом рассылает его."),
        ),
        error = err("write the minutes down of the meeting", "Устойчиво: take the minutes."),
        collocations = listOf(
            col("circulate the minutes", "разослать протокол"),
            col("action points", "поручения по итогам"),
        ),
    ),
    chunk(
        id = "put_in_the_hours",
        text = "put in the hours",
        translation = "вкалывать, много работать",
        headword = "hours",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("If you put in the hours, results will come.", "Если будешь вкалывать, результат придёт."),
        more = listOf(
            ex("He put in the hours to get the promotion.", "Он вкалывал, чтобы получить повышение."),
            ex("She's not lazy — she puts in the hours.", "Она не ленивая — она работает не покладая рук."),
        ),
        collocations = listOf(
            col("pull an all-nighter", "работать всю ночь"),
            col("go the extra mile", "выкладываться по полной"),
        ),
    ),
    chunk(
        id = "call_in_sick",
        text = "call in sick",
        translation = "отпроситься по болезни",
        headword = "sick",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + adv + adj",
        example = ex("He called in sick again this morning.", "Он снова отпросился по болезни сегодня утром."),
        more = listOf(
            ex("I had to call in sick with a migraine.", "Мне пришлось отпроситься с мигренью."),
            ex("Three people called in sick today.", "Сегодня трое не вышли по болезни."),
        ),
        collocations = listOf(
            col("be off sick", "быть на больничном"),
            col("a sick day", "день по болезни"),
        ),
    ),
    chunk(
        id = "meet_expectations",
        text = "meet expectations",
        translation = "оправдывать ожидания",
        headword = "expectations",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_2000,
        register = FORMAL,
        pattern = "V + N",
        example = ex(
            "The new hire has more than met our expectations.",
            "Новый сотрудник более чем оправдал наши ожидания.",
        ),
        more = listOf(
            ex("Sales failed to meet expectations this quarter.", "В этом квартале продажи не оправдали ожиданий."),
            ex(
                "Try to manage the client's expectations early.",
                "Постарайся сразу правильно выстроить ожидания клиента.",
            ),
        ),
        error = err("reach expectations", "С expectations используется meet, а не reach."),
        collocations = listOf(
            col("exceed expectations", "превзойти ожидания"),
            col("fall short of expectations", "не дотянуть до ожиданий"),
        ),
    ),
    chunk(
        id = "give_notice",
        text = "hand in your notice",
        translation = "подать заявление об увольнении",
        headword = "notice",
        theme = WORK,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + poss + N",
        example = ex("She handed in her notice on Monday.", "В понедельник она подала заявление об увольнении."),
        more = listOf(
            ex("You have to give a month's notice.", "Нужно предупредить за месяц."),
            ex("He worked his notice and left on good terms.", "Он отработал положенный срок и ушёл по-хорошему."),
        ),
        collocations = listOf(
            col("work your notice", "отработать срок предупреждения"),
            col("a notice period", "срок предупреждения об увольнении"),
        ),
    ),
    chunk(
        id = "climb_the_career_ladder",
        text = "climb the career ladder",
        translation = "делать карьеру, подниматься по карьерной лестнице",
        headword = "ladder",
        theme = WORK,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("She climbed the career ladder quickly.", "Она быстро поднялась по карьерной лестнице."),
        more = listOf(
            ex("Not everyone wants to climb the career ladder.", "Не все хотят делать карьеру."),
            ex(
                "He's more interested in the work than in climbing the ladder.",
                "Ему интереснее сама работа, чем карьера.",
            ),
        ),
        collocations = listOf(
            col("a step up", "повышение, шаг вперёд"),
            col("a dead-end job", "работа без перспектив"),
        ),
    ),
    chunk(
        id = "work_from_home",
        text = "work from home",
        translation = "работать из дома",
        headword = "home",
        theme = WORK,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + prep + N",
        example = ex("I work from home two days a week.", "Два дня в неделю я работаю из дома."),
        more = listOf(
            ex("The company lets everyone work from home.", "Компания разрешает всем работать из дома."),
            ex(
                "It's hard to switch off when you work from home.",
                "Тяжело отключиться от работы, когда работаешь из дома.",
            ),
        ),
        error = err(
            "work at home from the office",
            "«Работать из дома» — work from home; work at home тоже возможно, но менее частотно в этом смысле.",
        ),
        collocations = listOf(
            col("go into the office", "ездить в офис"),
            col("a hybrid schedule", "гибридный график"),
        ),
    ),
)
