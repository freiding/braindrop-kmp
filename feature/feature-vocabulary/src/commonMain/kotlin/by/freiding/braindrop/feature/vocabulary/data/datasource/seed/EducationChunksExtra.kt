package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.FORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.EDUCATION
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Education theme. */
internal val EDUCATION_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "sit_a_test",
        text = "sit a test",
        translation = "проходить тест",
        headword = "test",
        theme = EDUCATION,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("The whole class sat the test on Friday.", "В пятницу весь класс писал тест."),
        more = listOf(
            ex("You'll sit a placement test on the first day.", "В первый день вы напишете тест на уровень."),
            ex("He was too ill to sit the exam.", "Он был слишком болен, чтобы писать экзамен."),
        ),
        error = err(
            "write a test on the topic",
            "В британском английском чаще sit / take a test; «write a test» звучит иначе.",
        ),
        collocations = listOf(
            col("mark a test", "проверить тест"),
            col("a mock test", "пробный тест"),
        ),
    ),
    chunk(
        id = "meet_the_requirements",
        text = "meet the requirements",
        translation = "соответствовать требованиям",
        headword = "requirements",
        theme = EDUCATION,
        level = B2,
        frequencyBand = TOP_2000,
        register = FORMAL,
        pattern = "V + N",
        example = ex(
            "She meets all the entry requirements for the course.",
            "Она соответствует всем требованиям для поступления на курс.",
        ),
        more = listOf(
            ex(
                "If you don't meet the requirements, you can't apply.",
                "Если не соответствуешь требованиям, подать заявку нельзя.",
            ),
            ex("The essay didn't meet the word-count requirement.", "Эссе не соответствовало требованию по объёму."),
        ),
        error = err("answer the requirements", "С requirements используется meet, а не answer."),
        collocations = listOf(
            col("entry requirements", "требования для поступления"),
            col("fulfil the criteria", "удовлетворять критериям"),
        ),
    ),
    chunk(
        id = "catch_up_on_your_work",
        text = "catch up on your work",
        translation = "наверстать упущенное в учёбе",
        headword = "work",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + adv + prep + poss + N",
        example = ex("I spent the weekend catching up on my work.", "Все выходные я навёрстывал упущенное."),
        more = listOf(
            ex("She's catching up on the lessons she missed.", "Она навёрстывает пропущенные уроки."),
            ex("You'll have a lot to catch up on after the holiday.", "После каникул будет много чего наверстать."),
        ),
        collocations = listOf(
            col("fall behind", "отставать"),
            col("get up to date", "разобраться с накопившимся"),
        ),
    ),
    chunk(
        id = "read_up_on_a_topic",
        text = "read up on a topic",
        translation = "почитать по теме, подготовиться",
        headword = "read",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + prep + N",
        example = ex(
            "I need to read up on the topic before the seminar.",
            "Перед семинаром мне нужно почитать по теме.",
        ),
        more = listOf(
            ex(
                "She read up on the company before the interview.",
                "Перед собеседованием она изучила информацию о компании.",
            ),
            ex("Read up on it and we'll discuss it tomorrow.", "Почитай об этом, обсудим завтра."),
        ),
        collocations = listOf(
            col("do some background reading", "почитать общую литературу"),
            col("look something up", "посмотреть, найти информацию"),
        ),
    ),
    chunk(
        id = "give_a_talk",
        text = "give a talk",
        translation = "выступать с докладом",
        headword = "talk",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("She gave a talk on marine biology.", "Она выступила с докладом по морской биологии."),
        more = listOf(
            ex(
                "He's giving a talk at the conference next week.",
                "На следующей неделе он делает доклад на конференции.",
            ),
            ex("The talk was followed by a Q&A session.", "После доклада была сессия вопросов и ответов."),
        ),
        error = err("do a talk about", "«Выступать с докладом» — give a talk, не «do»."),
        collocations = listOf(
            col("a guest speaker", "приглашённый лектор"),
            col("field questions", "отвечать на вопросы из зала"),
        ),
    ),
    chunk(
        id = "get_full_marks",
        text = "get full marks",
        translation = "получить высший балл",
        headword = "marks",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adj + N",
        example = ex("She got full marks on the vocabulary test.", "Она получила высший балл за словарный тест."),
        more = listOf(
            ex(
                "You won't get full marks without showing your working.",
                "Без пояснений максимальный балл не поставят.",
            ),
            ex(
                "Full marks for effort, even if the answer's wrong.",
                "За старание — высший балл, даже если ответ неверный.",
            ),
        ),
        collocations = listOf(
            col("lose marks", "терять баллы"),
            col("a pass mark", "проходной балл"),
        ),
    ),
    chunk(
        id = "keep_up_with_the_class",
        text = "keep up with the class",
        translation = "успевать за классом",
        headword = "class",
        theme = EDUCATION,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + adv + prep + N",
        example = ex("He works hard to keep up with the class.", "Он усердно занимается, чтобы успевать за классом."),
        more = listOf(
            ex("If the pace is too fast, tell the teacher.", "Если темп слишком быстрый, скажи учителю."),
            ex("She had no trouble keeping up.", "Ей было легко успевать."),
        ),
        collocations = listOf(
            col("fall behind the others", "отстать от остальных"),
            col("be ahead of the class", "опережать класс"),
        ),
    ),
    chunk(
        id = "put_your_hand_up",
        text = "put your hand up",
        translation = "поднять руку",
        headword = "hand",
        theme = EDUCATION,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + poss + N + adv",
        example = ex("Put your hand up if you know the answer.", "Подними руку, если знаешь ответ."),
        more = listOf(
            ex("Nobody put their hand up.", "Никто не поднял руку."),
            ex("She put her hand up to ask a question.", "Она подняла руку, чтобы задать вопрос."),
        ),
        collocations = listOf(
            col("call on a student", "вызвать ученика"),
            col("speak up", "говорить громче"),
        ),
    ),
    chunk(
        id = "take_a_gap_year",
        text = "take a gap year",
        translation = "взять год перерыва перед вузом",
        headword = "gap year",
        theme = EDUCATION,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex(
            "She took a gap year to travel before university.",
            "Перед университетом она взяла год перерыва, чтобы попутешествовать.",
        ),
        more = listOf(
            ex("A gap year can look good on your CV.", "Год перерыва может хорошо смотреться в резюме."),
            ex("He spent his gap year volunteering.", "Год перерыва он провёл волонтёром."),
        ),
        collocations = listOf(
            col("defer your place", "отложить поступление"),
            col("go straight to university", "поступить сразу после школы"),
        ),
    ),
    chunk(
        id = "have_a_deadline_extended",
        text = "get an extension on a deadline",
        translation = "получить продление срока сдачи",
        headword = "extension",
        theme = EDUCATION,
        level = B2,
        frequencyBand = TOP_5000,
        register = FORMAL,
        pattern = "V + N + prep + N",
        example = ex("I asked for an extension on the assignment.", "Я попросил продлить срок сдачи задания."),
        more = listOf(
            ex("She got a week's extension because she was ill.", "Ей продлили срок на неделю из-за болезни."),
            ex("Extensions are only given in special cases.", "Продление даётся только в исключительных случаях."),
        ),
        collocations = listOf(
            col("apply for an extension", "подать на продление"),
            col("a firm deadline", "жёсткий срок"),
        ),
    ),
)
