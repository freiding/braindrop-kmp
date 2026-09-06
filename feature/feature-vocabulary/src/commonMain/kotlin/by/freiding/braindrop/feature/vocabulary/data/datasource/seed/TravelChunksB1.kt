package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.TRAVEL
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Travel theme. */
internal val TRAVEL_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "set_off_early",
        text = "set off early",
        translation = "выехать рано",
        headword = "set",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + adv",
        example = ex("We set off early to avoid the traffic.", "Мы выехали рано, чтобы избежать пробок."),
        more = listOf(
            ex("They set off at dawn and drove all day.", "Они тронулись на рассвете и ехали весь день."),
            ex("What time do we set off tomorrow?", "Во сколько мы завтра выезжаем?"),
        ),
        error = err("set out early from home for", "«Отправиться в путь» — set off / set out; после идёт for + место."),
        collocations = listOf(
            col("hit the road", "тронуться в путь"),
            col("beat the traffic", "выехать до пробок"),
        ),
    ),
    chunk(
        id = "make_a_reservation",
        text = "make a reservation",
        translation = "забронировать (столик, номер)",
        headword = "reservation",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I made a reservation for two at eight.", "Я забронировал столик на двоих на восемь."),
        more = listOf(
            ex("You should make a reservation at the weekend.", "На выходных лучше бронировать заранее."),
            ex("We had a reservation, but the table wasn't ready.", "У нас была бронь, но столик не подготовили."),
        ),
        error = err("do a reservation", "«Забронировать» — make a reservation, не «do»."),
        collocations = listOf(
            col("cancel a reservation", "отменить бронь"),
            col("under the name of", "на имя"),
        ),
    ),
    chunk(
        id = "run_into_problems",
        text = "run into problems",
        translation = "столкнуться с проблемами",
        headword = "problems",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + prep + N",
        example = ex("We ran into problems at the border.", "На границе мы столкнулись с проблемами."),
        more = listOf(
            ex("If you run into any trouble, call this number.", "Если возникнут проблемы, звони по этому номеру."),
            ex("The trip went fine — we didn't run into anything.", "Поездка прошла гладко — никаких проблем."),
        ),
        collocations = listOf(
            col("hit a snag", "наткнуться на загвоздку"),
            col("sort out a problem", "уладить проблему"),
        ),
    ),
    chunk(
        id = "catch_the_last_train",
        text = "catch the last train",
        translation = "успеть на последний поезд",
        headword = "train",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("If we leave now, we'll catch the last train.", "Если выйдем сейчас, успеем на последний поезд."),
        more = listOf(
            ex("She just caught the last train home.", "Она едва успела на последний поезд домой."),
            ex("We missed the last train and got a taxi.", "Мы опоздали на последний поезд и взяли такси."),
        ),
        collocations = listOf(
            col("miss your connection", "не успеть на пересадку"),
            col("the last one out", "последний рейс"),
        ),
    ),
    chunk(
        id = "get_a_lift",
        text = "get a lift",
        translation = "подъехать с кем-то, попроситься в машину",
        headword = "lift",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + N",
        example = ex("Can I get a lift to the station?", "Подбросишь меня до вокзала?"),
        more = listOf(
            ex("She gave me a lift home after work.", "После работы она подвезла меня домой."),
            ex("I got a lift with a neighbour.", "Меня подвёз сосед."),
        ),
        error = err(
            "take a lift with someone by car",
            "В брит. англ. «подвезти» — give someone a lift; get a lift — «доехать с кем-то».",
        ),
        collocations = listOf(
            col("drop someone off", "высадить кого-то"),
            col("car-share", "ездить вместе на одной машине"),
        ),
    ),
    chunk(
        id = "book_time_off",
        text = "book time off",
        translation = "оформить отпуск",
        headword = "time off",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("I've booked time off for the trip in July.", "Я оформил отпуск на поездку в июле."),
        more = listOf(
            ex("Book your time off before someone else does.", "Оформи отпуск, пока это не сделал кто-то другой."),
            ex("She couldn't book the time off she wanted.", "Ей не удалось взять отпуск на нужные даты."),
        ),
        collocations = listOf(
            col("take annual leave", "взять ежегодный отпуск"),
            col("use up your holiday", "потратить отпускные дни"),
        ),
    ),
    chunk(
        id = "stop_over_in_a_city",
        text = "stop over in a city",
        translation = "сделать остановку в городе",
        headword = "stop",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + adv + prep + N",
        example = ex("We stopped over in Singapore for two nights.", "Мы сделали остановку в Сингапуре на две ночи."),
        more = listOf(
            ex("You can stop over in Reykjavik for free.", "В Рейкьявике можно сделать бесплатную остановку."),
            ex("They stopped over on the way back too.", "На обратном пути они тоже сделали остановку."),
        ),
        collocations = listOf(
            col("a layover", "пересадка с ожиданием"),
            col("break the journey", "разбить поездку на этапы"),
        ),
    ),
    chunk(
        id = "keep_an_eye_on_your_bags",
        text = "keep an eye on your bags",
        translation = "присматривать за вещами",
        headword = "eye",
        theme = TRAVEL,
        level = B1,
        frequencyBand = TOP_1000,
        register = INFORMAL,
        pattern = "V + N + prep + poss + N",
        example = ex("Keep an eye on your bags at the station.", "Присматривай за вещами на вокзале."),
        more = listOf(
            ex("Could you keep an eye on my case for a minute?", "Присмотришь минутку за моим чемоданом?"),
            ex("Keep an eye on the time — boarding starts soon.", "Следи за временем — скоро посадка."),
        ),
        collocations = listOf(
            col("watch your belongings", "следить за вещами"),
            col("leave luggage unattended", "оставить багаж без присмотра"),
        ),
    ),
)
