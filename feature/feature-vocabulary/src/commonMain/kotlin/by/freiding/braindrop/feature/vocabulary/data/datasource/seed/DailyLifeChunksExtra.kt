package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.DAILY_LIFE
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Daily life theme. */
internal val DAILY_LIFE_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "do_the_ironing",
        text = "do the ironing",
        translation = "гладить бельё",
        headword = "ironing",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_5000,
        pattern = "V + N",
        example = ex("I hate doing the ironing.", "Терпеть не могу гладить."),
        more = listOf(
            ex("He does the ironing while watching TV.", "Он гладит под телевизор."),
            ex("There's a huge pile of ironing to do.", "Скопилась огромная гора глажки."),
        ),
        error = err("make the ironing", "«Гладить» — do the ironing, не «make»."),
        collocations = listOf(
            col("hang out the washing", "развесить бельё"),
            col("fold the laundry", "сложить бельё"),
        ),
    ),
    chunk(
        id = "take_out_the_rubbish",
        text = "take out the rubbish",
        translation = "выносить мусор",
        headword = "rubbish",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("It's your turn to take out the rubbish.", "Твоя очередь выносить мусор."),
        more = listOf(
            ex("He forgot to take the rubbish out again.", "Он снова забыл вынести мусор."),
            ex("The bins are collected on Tuesdays.", "Мусор вывозят по вторникам."),
        ),
        collocations = listOf(
            col("empty the bin", "опустошить мусорное ведро"),
            col("sort the recycling", "рассортировать вторсырьё"),
        ),
    ),
    chunk(
        id = "run_out_of_milk",
        text = "run out of milk",
        translation = "остаться без молока",
        headword = "milk",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + adv + prep + N",
        example = ex("We've run out of milk again.", "У нас снова кончилось молоко."),
        more = listOf(
            ex("The printer has run out of ink.", "В принтере закончились чернила."),
            ex("Don't let us run out of coffee.", "Проследи, чтобы кофе не кончился."),
        ),
        error = err("run out milk", "После run out идёт of: run out of something."),
        collocations = listOf(
            col("be low on something", "почти закончиться"),
            col("stock up on something", "закупиться чем-то"),
        ),
    ),
    chunk(
        id = "get_ready_for_work",
        text = "get ready for work",
        translation = "собираться на работу",
        headword = "ready",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + adj + prep + N",
        example = ex("I get ready for work in twenty minutes.", "Я собираюсь на работу за двадцать минут."),
        more = listOf(
            ex("She was still getting ready when the taxi arrived.", "Она ещё собиралась, когда приехало такси."),
            ex("Get the kids ready for school, please.", "Собери детей в школу, пожалуйста."),
        ),
        collocations = listOf(
            col("be running late", "опаздывать"),
            col("grab breakfast", "перехватить завтрак"),
        ),
    ),
    chunk(
        id = "nip_to_the_shops",
        text = "nip to the shops",
        translation = "сбегать в магазин",
        headword = "shops",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + prep + N",
        example = ex("I'm just going to nip to the shops for bread.", "Я сбегаю в магазин за хлебом."),
        more = listOf(
            ex("Can you nip to the shop while I cook?", "Сбегаешь в магазин, пока я готовлю?"),
            ex("She nipped out for five minutes.", "Она выскочила на пять минут."),
        ),
        collocations = listOf(
            col("pop to the shop", "заскочить в магазин"),
            col("do a big shop", "закупиться на неделю"),
        ),
    ),
    chunk(
        id = "charge_your_phone",
        text = "charge your phone",
        translation = "заряжать телефон",
        headword = "phone",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + poss + N",
        example = ex("Don't forget to charge your phone tonight.", "Не забудь зарядить телефон на ночь."),
        more = listOf(
            ex("My phone died — I forgot to charge it.", "Телефон разрядился — я забыл его зарядить."),
            ex("Is there somewhere I can charge my phone?", "Где-нибудь можно зарядить телефон?"),
        ),
        error = err("load your phone", "«Зарядить телефон» — charge your phone, не «load»."),
        collocations = listOf(
            col("your phone dies", "телефон разряжается"),
            col("run low on battery", "садится батарея"),
        ),
    ),
    chunk(
        id = "get_the_house_tidy",
        text = "tidy up the house",
        translation = "прибраться в доме",
        headword = "tidy",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("Let's tidy up the house before they arrive.", "Давай приберёмся в доме до их прихода."),
        more = listOf(
            ex("He tidied up his room without being asked.", "Он прибрался в комнате, хотя его не просили."),
            ex("It only takes ten minutes to tidy up.", "На уборку уходит всего десять минут."),
        ),
        collocations = listOf(
            col("declutter", "разобрать хлам"),
            col("do a spring clean", "устроить генеральную уборку"),
        ),
    ),
    chunk(
        id = "wait_in_for_a_delivery",
        text = "wait in for a delivery",
        translation = "сидеть дома в ожидании доставки",
        headword = "delivery",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + prep + N",
        example = ex(
            "I have to wait in for a delivery all morning.",
            "Всё утро мне придётся сидеть дома и ждать доставку.",
        ),
        more = listOf(
            ex("She waited in all day but nothing came.", "Она прождала весь день, но ничего не привезли."),
            ex("They give you a two-hour delivery window.", "Тебе называют двухчасовое окно доставки."),
        ),
        collocations = listOf(
            col("track a parcel", "отслеживать посылку"),
            col("a missed delivery", "неудавшаяся доставка"),
        ),
    ),
    chunk(
        id = "get_a_takeaway",
        text = "get a takeaway",
        translation = "заказать еду навынос",
        headword = "takeaway",
        theme = DAILY_LIFE,
        level = A2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N",
        example = ex("Shall we just get a takeaway tonight?", "Может, просто закажем еду навынос сегодня?"),
        more = listOf(
            ex("We got a takeaway and watched a film.", "Мы заказали еду навынос и посмотрели фильм."),
            ex("There's a good Thai takeaway round the corner.", "За углом есть хорошая тайская еда навынос."),
        ),
        collocations = listOf(
            col("order in", "заказать домой"),
            col("eat out", "поужинать в кафе"),
        ),
    ),
    chunk(
        id = "settle_down_for_the_evening",
        text = "settle down for the evening",
        translation = "устроиться на вечер дома",
        headword = "settle",
        theme = DAILY_LIFE,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + prep + N",
        example = ex("We settled down for the evening with a film.", "Мы устроились на вечер с фильмом."),
        more = listOf(
            ex("Just as I settled down, the phone rang.", "Только я устроился, как зазвонил телефон."),
            ex("The kids finally settled down at nine.", "Дети наконец угомонились в девять."),
        ),
        collocations = listOf(
            col("put your feet up", "дать ногам отдохнуть"),
            col("unwind after work", "расслабиться после работы"),
        ),
    ),
)
