package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.HEALTH
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Health theme. */
internal val HEALTH_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "take_it_easy_for_a_few_days",
        text = "take it easy for a few days",
        translation = "поберечь себя несколько дней",
        headword = "easy",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_1000,
        register = INFORMAL,
        pattern = "V + pron + adj + prep + N",
        example = ex(
            "The doctor told me to take it easy for a few days.",
            "Врач сказал мне несколько дней поберечься.",
        ),
        more = listOf(
            ex("Take it easy — you've only just recovered.", "Не перенапрягайся — ты только поправился."),
            ex("He's taking it easy after the operation.", "После операции он бережёт себя."),
        ),
        collocations = listOf(
            col("ease back into exercise", "постепенно вернуться к тренировкам"),
            col("give yourself time to recover", "дать себе время восстановиться"),
        ),
    ),
    chunk(
        id = "stay_hydrated",
        text = "stay hydrated",
        translation = "поддерживать водный баланс",
        headword = "hydrated",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + adj",
        example = ex("Drink water regularly to stay hydrated.", "Пей воду регулярно, чтобы не было обезвоживания."),
        more = listOf(
            ex("It's important to stay hydrated in hot weather.", "В жару важно поддерживать водный баланс."),
            ex("She wasn't drinking enough and got dehydrated.", "Она мало пила и получила обезвоживание."),
        ),
        collocations = listOf(
            col("keep your fluids up", "пить достаточно жидкости"),
            col("get dehydrated", "получить обезвоживание"),
        ),
    ),
    chunk(
        id = "work_up_a_sweat",
        text = "work up a sweat",
        translation = "как следует пропотеть, разогреться",
        headword = "sweat",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("A brisk walk is enough to work up a sweat.", "Быстрой ходьбы хватает, чтобы пропотеть."),
        more = listOf(
            ex(
                "He works up a sweat every morning on the bike.",
                "Каждое утро он гоняет на велосипеде до седьмого пота.",
            ),
            ex("You don't need a gym to work up a sweat.", "Чтобы вспотеть, зал не нужен."),
        ),
        collocations = listOf(
            col("break a sweat", "вспотеть от нагрузки"),
            col("get your heart rate up", "поднять пульс"),
        ),
    ),
    chunk(
        id = "book_a_session_with_a_physio",
        text = "book a session with a physio",
        translation = "записаться к физиотерапевту",
        headword = "physio",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N + prep + N",
        example = ex("I booked a session with a physio for my knee.", "Я записался к физиотерапевту из-за колена."),
        more = listOf(
            ex("The physio gave me exercises to do at home.", "Физиотерапевт дал упражнения для дома."),
            ex("A few sessions with a physio made a big difference.", "Пара сеансов у физиотерапевта заметно помогла."),
        ),
        collocations = listOf(
            col("do your exercises", "выполнять предписанные упражнения"),
            col("build up strength", "наращивать силу"),
        ),
    ),
    chunk(
        id = "come_out_in_a_rash",
        text = "come out in a rash",
        translation = "покрыться сыпью",
        headword = "rash",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + adv + prep + N",
        example = ex("She came out in a rash after eating shellfish.", "После морепродуктов её обсыпало."),
        more = listOf(
            ex("His skin came out in a rash from the new soap.", "От нового мыла у него на коже появилась сыпь."),
            ex("If you come out in a rash, stop taking it.", "Если появится сыпь, прекрати приём."),
        ),
        error = err("get out in a rash", "Устойчиво: come out in a rash / break out in a rash."),
        collocations = listOf(
            col("have an allergic reaction", "получить аллергическую реакцию"),
            col("an itchy patch", "зудящее пятно"),
        ),
    ),
    chunk(
        id = "get_something_checked_out",
        text = "get something checked out",
        translation = "показать что-то врачу, обследоваться",
        headword = "checked",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + N + V-ed + adv",
        example = ex("You should get that cough checked out.", "Тебе стоит показать этот кашель врачу."),
        more = listOf(
            ex(
                "She got the lump checked out — it was nothing.",
                "Она обследовала уплотнение — оказалось, ничего страшного.",
            ),
            ex("Get it checked out sooner rather than later.", "Лучше обследоваться раньше, чем позже."),
        ),
        collocations = listOf(
            col("have some tests done", "сдать анализы"),
            col("rule something out", "исключить что-то"),
        ),
    ),
    chunk(
        id = "be_run_down",
        text = "be run down",
        translation = "быть измотанным, ослабленным",
        headword = "run down",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + V-ed + adv",
        example = ex("I've been feeling run down for weeks.", "Уже несколько недель я чувствую себя измотанным."),
        more = listOf(
            ex("You look run down — get some rest.", "Ты выглядишь измотанным — отдохни."),
            ex("Being run down makes you catch every cold.", "Когда организм ослаблен, цепляешь любую простуду."),
        ),
        collocations = listOf(
            col("be worn out", "быть без сил"),
            col("build yourself back up", "восстановить силы"),
        ),
    ),
    chunk(
        id = "ease_the_pain",
        text = "ease the pain",
        translation = "облегчить боль",
        headword = "pain",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("Ibuprofen should ease the pain.", "Ибупрофен должен облегчить боль."),
        more = listOf(
            ex("A hot bath eased the pain in her back.", "Горячая ванна облегчила боль в спине."),
            ex("Nothing seemed to ease the pain.", "Ничто, казалось, не унимало боль."),
        ),
        collocations = listOf(
            col("relieve the symptoms", "снять симптомы"),
            col("a dull ache", "тупая боль"),
        ),
    ),
)
