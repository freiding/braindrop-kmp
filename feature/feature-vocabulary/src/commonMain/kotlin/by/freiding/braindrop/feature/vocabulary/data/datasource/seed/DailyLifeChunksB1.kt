package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.DAILY_LIFE
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Daily life theme. */
internal val DAILY_LIFE_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "stay_on_top_of_the_chores",
        text = "stay on top of the chores",
        translation = "справляться с домашними делами",
        headword = "chores",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + prep + N + prep + N",
        example = ex("It's hard to stay on top of the chores with a baby.", "С малышом трудно успевать по хозяйству."),
        more = listOf(
            ex("We split the chores to stay on top of them.", "Мы делим дела по дому, чтобы всё успевать."),
            ex("Do a bit each day and you'll stay on top of it.", "Делай понемногу каждый день — и не запустишь."),
        ),
        collocations = listOf(
            col("let things pile up", "запустить дела"),
            col("get on top of the housework", "разгрести домашние дела"),
        ),
    ),
    chunk(
        id = "sort_out_the_paperwork",
        text = "sort out the paperwork",
        translation = "разобраться с бумагами",
        headword = "paperwork",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("I spent Sunday sorting out the paperwork.", "Воскресенье я потратил на разбор бумаг."),
        more = listOf(
            ex("There's a lot of paperwork to sort out after a move.", "После переезда куча бумажной волокиты."),
            ex("She sorts out the paperwork; he does the cooking.", "Она занимается документами, он — готовкой."),
        ),
        collocations = listOf(
            col("fill in a form", "заполнить бланк"),
            col("cut through the red tape", "пробиться сквозь бюрократию"),
        ),
    ),
    chunk(
        id = "do_the_school_run",
        text = "do the school run",
        translation = "возить детей в школу и обратно",
        headword = "run",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N",
        example = ex("I do the school run every morning.", "Каждое утро я вожу детей в школу."),
        more = listOf(
            ex("Can you do the school run tomorrow?", "Отвезёшь завтра детей в школу?"),
            ex("The school run takes an hour with the traffic.", "С пробками дорога в школу занимает час."),
        ),
        collocations = listOf(
            col("drop the kids off", "высадить детей"),
            col("the morning rush", "утренняя суета"),
        ),
    ),
    chunk(
        id = "get_around_to_something",
        text = "get around to something",
        translation = "дойти до чего-то, наконец заняться",
        headword = "around",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_1000,
        register = INFORMAL,
        pattern = "V + adv + prep + N",
        example = ex("I finally got around to fixing the door.", "Я наконец добрался до починки двери."),
        more = listOf(
            ex("She never gets around to answering emails.", "У неё вечно не доходят руки до писем."),
            ex("We'll get around to it at the weekend.", "Займёмся этим на выходных."),
        ),
        error = err("come around to do it", "«Наконец заняться» — get around to doing something."),
        collocations = listOf(
            col("put something off", "откладывать что-то"),
            col("get it out of the way", "разделаться с этим"),
        ),
    ),
    chunk(
        id = "run_a_bath",
        text = "run a bath",
        translation = "набрать ванну",
        headword = "bath",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("I'm going to run a bath and relax.", "Наберу ванну и расслаблюсь."),
        more = listOf(
            ex("She ran a bath for the kids.", "Она набрала детям ванну."),
            ex("Run the bath while I get the towels.", "Набери ванну, пока я достаю полотенца."),
        ),
        error = err("make a bath", "«Набрать ванну» — run a bath, не «make»."),
        collocations = listOf(
            col("have a soak", "полежать в ванне"),
            col("jump in the shower", "быстро принять душ"),
        ),
    ),
    chunk(
        id = "keep_on_top_of_the_bills",
        text = "keep on top of the bills",
        translation = "вовремя оплачивать счета",
        headword = "bills",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N + prep + N",
        example = ex(
            "We use an app to keep on top of the bills.",
            "Мы пользуемся приложением, чтобы не пропускать счета.",
        ),
        more = listOf(
            ex("As long as you keep on top of them, it's fine.", "Пока платишь вовремя, всё в порядке."),
            ex("He fell behind and couldn't keep on top of the bills.", "Он запустил дела и не справлялся со счетами."),
        ),
        collocations = listOf(
            col("set up a direct debit", "настроить автоплатёж"),
            col("a final reminder", "последнее напоминание об оплате"),
        ),
    ),
    chunk(
        id = "pop_out_for_a_bit",
        text = "pop out for a bit",
        translation = "выйти ненадолго",
        headword = "pop",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + prep + N",
        example = ex(
            "I'm just popping out for a bit — back in ten.",
            "Я выскочу ненадолго — вернусь через десять минут.",
        ),
        more = listOf(
            ex("She popped out to post a letter.", "Она вышла отправить письмо."),
            ex("Can you watch the pan? I'm popping out.", "Присмотришь за кастрюлей? Я на минутку выйду."),
        ),
        collocations = listOf(
            col("nip out", "выскочить на минутку"),
            col("be back shortly", "скоро вернуться"),
        ),
    ),
    chunk(
        id = "get_a_quote",
        text = "get a quote",
        translation = "получить смету, расчёт цены",
        headword = "quote",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex(
            "Get a few quotes before you choose a builder.",
            "Возьми несколько смет, прежде чем выбрать строителя.",
        ),
        more = listOf(
            ex("The plumber gave me a quote for the job.", "Сантехник назвал цену за работу."),
            ex("That quote seems high — shop around.", "Цена кажется завышенной — поищи ещё."),
        ),
        collocations = listOf(
            col("compare quotes", "сравнить сметы"),
            col("a rough estimate", "примерная оценка"),
        ),
    ),
)
