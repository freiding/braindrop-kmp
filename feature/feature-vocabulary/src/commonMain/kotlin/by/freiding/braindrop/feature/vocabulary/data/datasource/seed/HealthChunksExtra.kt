package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.HEALTH
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Health theme. */
internal val HEALTH_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "have_a_temperature",
        text = "have a temperature",
        translation = "иметь температуру",
        headword = "temperature",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("She's got a temperature and a sore throat.", "У неё температура и болит горло."),
        more = listOf(
            ex("Does he have a temperature?", "У него есть температура?"),
            ex("Take some paracetamol if you have a temperature.", "Прими парацетамол, если есть температура."),
        ),
        error = err("have temperature", "Нужен артикль: have a temperature."),
        collocations = listOf(
            col("run a fever", "температурить"),
            col("take someone's temperature", "измерить температуру"),
        ),
    ),
    chunk(
        id = "book_a_doctors_appointment",
        text = "book a doctor's appointment",
        translation = "записаться к врачу",
        headword = "appointment",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I've booked a doctor's appointment for Thursday.", "Я записался к врачу на четверг."),
        more = listOf(
            ex("You can book an appointment through the app.", "Записаться на приём можно через приложение."),
            ex(
                "She couldn't get an appointment for two weeks.",
                "Записаться раньше чем через две недели не получилось.",
            ),
        ),
        collocations = listOf(
            col("cancel an appointment", "отменить приём"),
            col("the waiting room", "приёмная"),
        ),
    ),
    chunk(
        id = "pick_up_a_prescription",
        text = "pick up a prescription",
        translation = "забрать лекарства по рецепту",
        headword = "prescription",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + adv + N",
        example = ex("I need to pick up a prescription from the pharmacy.", "Мне нужно забрать лекарства в аптеке."),
        more = listOf(
            ex("The doctor wrote me a prescription for antibiotics.", "Врач выписал мне рецепт на антибиотики."),
            ex("Your prescription will be ready in an hour.", "Ваш заказ по рецепту будет готов через час."),
        ),
        collocations = listOf(
            col("fill a prescription", "приготовить лекарство по рецепту"),
            col("over the counter", "без рецепта"),
        ),
    ),
    chunk(
        id = "get_a_second_opinion",
        text = "get a second opinion",
        translation = "проконсультироваться у другого врача",
        headword = "opinion",
        theme = HEALTH,
        level = B2,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex(
            "She got a second opinion before the operation.",
            "Перед операцией она проконсультировалась ещё у одного врача.",
        ),
        more = listOf(
            ex("It's worth getting a second opinion on this.", "По этому вопросу стоит услышать второе мнение."),
            ex("He wanted a second opinion on the diagnosis.", "Он хотел уточнить диагноз у другого специалиста."),
        ),
        collocations = listOf(
            col("see a specialist", "обратиться к специалисту"),
            col("be referred to hospital", "получить направление в больницу"),
        ),
    ),
    chunk(
        id = "shake_off_a_cold",
        text = "shake off a cold",
        translation = "избавиться от простуды",
        headword = "cold",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("I can't seem to shake off this cold.", "Никак не могу избавиться от этой простуды."),
        more = listOf(
            ex("She shook it off after a couple of days.", "Через пару дней она с этим справилась."),
            ex("Rest and fluids will help you shake it off.", "Отдых и обильное питьё помогут поправиться."),
        ),
        collocations = listOf(
            col("get over an illness", "переболеть"),
            col("be on the mend", "идти на поправку"),
        ),
    ),
    chunk(
        id = "watch_what_you_eat",
        text = "watch what you eat",
        translation = "следить за питанием",
        headword = "eat",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + clause",
        example = ex("I've been watching what I eat since January.", "С января я слежу за тем, что ем."),
        more = listOf(
            ex(
                "You don't have to diet — just watch what you eat.",
                "Не обязательно сидеть на диете — просто следи за питанием.",
            ),
            ex("He watches his weight but still enjoys food.", "Он следит за весом, но не отказывает себе в еде."),
        ),
        collocations = listOf(
            col("count calories", "считать калории"),
            col("cut out junk food", "отказаться от фастфуда"),
        ),
    ),
    chunk(
        id = "go_for_a_check_up",
        text = "go for a check-up",
        translation = "сходить на осмотр",
        headword = "check-up",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + prep + N",
        example = ex("I'm going for a check-up next week.", "На следующей неделе я иду на осмотр."),
        more = listOf(
            ex("Go for a check-up if the pain comes back.", "Сходи на осмотр, если боль вернётся."),
            ex("Everything was fine at the check-up.", "На осмотре всё было в порядке."),
        ),
        collocations = listOf(
            col("a routine check-up", "плановый осмотр"),
            col("get some blood tests done", "сдать анализы крови"),
        ),
    ),
    chunk(
        id = "pull_a_muscle",
        text = "pull a muscle",
        translation = "потянуть мышцу",
        headword = "muscle",
        theme = HEALTH,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("He pulled a muscle in his back lifting boxes.", "Он потянул мышцу спины, поднимая коробки."),
        more = listOf(
            ex("I pulled a muscle at the gym yesterday.", "Вчера в зале я потянул мышцу."),
            ex("Warm up properly so you don't pull a muscle.", "Хорошо разомнись, чтобы не потянуть мышцу."),
        ),
        collocations = listOf(
            col("strain your back", "надорвать спину"),
            col("be stiff", "чувствовать скованность в мышцах"),
        ),
    ),
    chunk(
        id = "get_back_on_your_feet",
        text = "get back on your feet",
        translation = "встать на ноги, поправиться",
        headword = "feet",
        theme = HEALTH,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + prep + poss + N",
        example = ex(
            "It took her a month to get back on her feet.",
            "Ей понадобился месяц, чтобы снова встать на ноги.",
        ),
        more = listOf(
            ex("The medicine helped him get back on his feet.", "Лекарство помогло ему поправиться."),
            ex("We'll help you get back on your feet.", "Мы поможем тебе встать на ноги."),
        ),
        collocations = listOf(
            col("make a full recovery", "полностью выздороветь"),
            col("take it easy", "не перенапрягаться"),
        ),
    ),
    chunk(
        id = "have_an_early_night",
        text = "have an early night",
        translation = "лечь спать пораньше",
        headword = "night",
        theme = HEALTH,
        level = A2,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + adj + N",
        example = ex("I'm exhausted — I'll have an early night.", "Я вымотан — лягу спать пораньше."),
        more = listOf(
            ex("You look tired; have an early night.", "Ты выглядишь уставшим, ложись пораньше."),
            ex("An early night made all the difference.", "Ранний отбой очень помог."),
        ),
        collocations = listOf(
            col("catch up on sleep", "выспаться, наверстать сон"),
            col("have a lie-in", "поспать подольше утром"),
        ),
    ),
)
