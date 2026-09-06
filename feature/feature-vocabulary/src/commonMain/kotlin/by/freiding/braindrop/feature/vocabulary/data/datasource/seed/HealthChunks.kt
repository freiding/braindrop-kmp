package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.HEALTH
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

internal val HEALTH_CHUNKS: List<Chunk> = listOf(
    chunk(
        id = "catch_a_cold",
        text = "catch a cold",
        translation = "простудиться",
        headword = "cold",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("Put a coat on or you'll catch a cold.", "Надень куртку, а то простудишься."),
        more = listOf(
            ex("She caught a cold on the plane.", "Она простудилась в самолёте."),
            ex("Every winter the kids catch a cold.", "Каждую зиму дети простужаются."),
        ),
        error = err(
            "get ill with a cold",
            "«Простудиться» — catch a cold; «get a cold» тоже верно, но не «get ill with».",
        ),
        collocations = listOf(
            col("come down with something", "слечь с болезнью"),
            col("get over a cold", "справиться с простудой"),
            col("a runny nose", "насморк"),
        ),
    ),
    chunk(
        id = "make_an_appointment",
        text = "make an appointment",
        translation = "записаться на приём",
        headword = "appointment",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I need to make an appointment with the dentist.", "Мне нужно записаться к стоматологу."),
        more = listOf(
            ex("She made an appointment for Thursday morning.", "Она записалась на приём в четверг утром."),
            ex("You can make an appointment online.", "Записаться на приём можно онлайн."),
        ),
        error = err("take an appointment", "«Записаться на приём» — make an appointment, не «take»."),
        collocations = listOf(
            col("cancel an appointment", "отменить приём"),
            col("a doctor's appointment", "приём у врача"),
        ),
    ),
    chunk(
        id = "take_medicine",
        text = "take medicine",
        translation = "принимать лекарство",
        headword = "medicine",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex(
            "Take this medicine twice a day after meals.",
            "Принимайте это лекарство дважды в день после еды.",
        ),
        more = listOf(
            ex("He hates taking medicine.", "Он терпеть не может пить лекарства."),
            ex("Did you take your medicine this morning?", "Ты принял лекарство утром?"),
        ),
        error = err("drink medicine", "Лекарства по-английски take, а не «drink»."),
        collocations = listOf(
            col("take a painkiller", "принять обезболивающее"),
            col("a course of antibiotics", "курс антибиотиков"),
        ),
    ),
    chunk(
        id = "have_a_check_up",
        text = "have a check-up",
        translation = "проходить осмотр",
        headword = "check-up",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("I have a check-up once a year.", "Раз в год я прохожу медосмотр."),
        more = listOf(
            ex("The doctor said everything looked fine at the check-up.", "На осмотре врач сказал, что всё в порядке."),
            ex("Book a check-up if the pain doesn't go away.", "Запишись на осмотр, если боль не проходит."),
        ),
        collocations = listOf(
            col("a routine check-up", "плановый осмотр"),
            col("run some tests", "сделать анализы"),
        ),
    ),
    chunk(
        id = "get_some_rest",
        text = "get some rest",
        translation = "отдохнуть",
        headword = "rest",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("You look tired — go and get some rest.", "Ты выглядишь уставшим — иди отдохни."),
        more = listOf(
            ex("She needs to get some rest before the trip.", "Ей нужно отдохнуть перед поездкой."),
            ex("Get plenty of rest and drink water.", "Побольше отдыхай и пей воду."),
        ),
        collocations = listOf(
            col("have an early night", "лечь пораньше"),
            col("take it easy", "не перенапрягаться"),
        ),
    ),
    chunk(
        id = "work_out_at_the_gym",
        text = "work out at the gym",
        translation = "тренироваться в зале",
        headword = "gym",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + adv + prep + N",
        example = ex("She works out at the gym three times a week.", "Она тренируется в зале три раза в неделю."),
        more = listOf(
            ex("I've started working out at the gym before work.", "Я начал ходить в зал перед работой."),
            ex("He works out at home instead of the gym.", "Он тренируется дома, а не в зале."),
        ),
        error = err("make sport at the gym", "«Заниматься спортом» — work out / do exercise, не «make sport»."),
        collocations = listOf(
            col("go for a run", "пойти на пробежку"),
            col("get in shape", "привести себя в форму"),
            col("lift weights", "поднимать веса"),
        ),
        forms = listOf(verb("exercise"), noun("workout")),
    ),
    chunk(
        id = "put_on_weight",
        text = "put on weight",
        translation = "набирать вес",
        headword = "weight",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("I've put on weight over the winter.", "За зиму я набрал вес."),
        more = listOf(
            ex("He put on weight after he stopped playing football.", "Он набрал вес, когда бросил футбол."),
            ex("Stress made her put on weight.", "Из-за стресса она набрала вес."),
        ),
        error = err("get fat weight", "«Набрать вес» — put on weight (или gain weight)."),
        collocations = listOf(
            col("lose weight", "худеть"),
            col("watch your weight", "следить за весом"),
        ),
    ),
    chunk(
        id = "come_down_with_the_flu",
        text = "come down with the flu",
        translation = "слечь с гриппом",
        headword = "flu",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + prep + N",
        example = ex("Half the office has come down with the flu.", "Половина офиса слегла с гриппом."),
        more = listOf(
            ex("She came down with the flu the day before the exam.", "Она слегла с гриппом за день до экзамена."),
            ex("I think I'm coming down with something.", "Кажется, я заболеваю."),
        ),
        collocations = listOf(
            col("be off sick", "болеть / не выйти на работу"),
            col("a nasty bug", "противная простуда"),
        ),
    ),
    chunk(
        id = "get_a_good_nights_sleep",
        text = "get a good night's sleep",
        translation = "хорошо выспаться",
        headword = "sleep",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("Get a good night's sleep before the interview.", "Хорошо выспись перед собеседованием."),
        more = listOf(
            ex("I never get a good night's sleep on trains.", "В поездах я никогда толком не высыпаюсь."),
            ex("After a good night's sleep, everything seemed easier.", "После хорошего сна всё казалось проще."),
        ),
        collocations = listOf(
            col("have trouble sleeping", "плохо спать"),
            col("a sleepless night", "бессонная ночь"),
        ),
    ),
    chunk(
        id = "break_a_leg_injury",
        text = "break your leg",
        translation = "сломать ногу",
        headword = "leg",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + poss + N",
        example = ex("He broke his leg skiing last year.", "В прошлом году он сломал ногу на лыжах."),
        more = listOf(
            ex("She broke her arm falling off a bike.", "Она сломала руку, упав с велосипеда."),
            ex("If you don't slow down, you'll break your leg.", "Если не сбавишь скорость, сломаешь ногу."),
        ),
        collocations = listOf(
            col("sprain your ankle", "растянуть лодыжку"),
            col("be in plaster", "быть в гипсе"),
            col("in a cast", "в гипсе"),
        ),
    ),
    chunk(
        id = "cut_down_on_sugar",
        text = "cut down on sugar",
        translation = "сократить потребление сахара",
        headword = "sugar",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + prep + N",
        example = ex("The doctor told me to cut down on sugar.", "Врач сказал мне сократить потребление сахара."),
        more = listOf(
            ex("She's cutting down on sugar and salt.", "Она сокращает сахар и соль в рационе."),
            ex("Try to cut down on sugar gradually.", "Постарайся сокращать сахар постепенно."),
        ),
        collocations = listOf(
            col("cut out caffeine", "полностью отказаться от кофеина"),
            col("a balanced diet", "сбалансированное питание"),
        ),
    ),
    chunk(
        id = "feel_under_the_weather",
        text = "feel under the weather",
        translation = "неважно себя чувствовать",
        headword = "weather",
        theme = HEALTH,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + idiom",
        example = ex("I'm feeling a bit under the weather today.", "Сегодня я неважно себя чувствую."),
        more = listOf(
            ex("He's been under the weather all week.", "Он всю неделю не в форме."),
            ex("If you feel under the weather, stay home.", "Если чувствуешь себя плохо, оставайся дома."),
        ),
        collocations = listOf(
            col("be off colour", "выглядеть неважно"),
            col("feel run down", "чувствовать себя измотанным"),
        ),
    ),
    chunk(
        id = "keep_fit",
        text = "keep fit",
        translation = "поддерживать форму",
        headword = "fit",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + adj",
        example = ex(
            "Cycling to work helps me keep fit.",
            "Поездки на работу на велосипеде помогают мне держать форму.",
        ),
        more = listOf(
            ex("She keeps fit by swimming every day.", "Она поддерживает форму, плавая каждый день."),
            ex("It's harder to keep fit as you get older.", "С возрастом держать форму сложнее."),
        ),
        collocations = listOf(
            col("stay active", "вести активный образ жизни"),
            col("be in good shape", "быть в хорошей форме"),
        ),
        forms = listOf(adj("fit"), noun("fitness")),
    ),
)
