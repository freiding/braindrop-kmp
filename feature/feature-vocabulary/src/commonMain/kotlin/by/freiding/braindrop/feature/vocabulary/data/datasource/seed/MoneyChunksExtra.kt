package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.A2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B2
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.MONEY
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** Second content drop for the Money theme. */
internal val MONEY_CHUNKS_EXTRA: List<Chunk> = listOf(
    chunk(
        id = "earn_a_living",
        text = "earn a living",
        translation = "зарабатывать на жизнь",
        headword = "living",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("He earns a living as a translator.", "Он зарабатывает на жизнь переводами."),
        more = listOf(
            ex("It's hard to earn a living from music.", "Трудно зарабатывать на жизнь музыкой."),
            ex("What does she do for a living?", "Чем она зарабатывает на жизнь?"),
        ),
        error = err("win a living", "«Зарабатывать на жизнь» — earn a living, не «win»."),
        collocations = listOf(
            col("make ends meet", "сводить концы с концами"),
            col("a decent wage", "приличная зарплата"),
        ),
        forms = listOf(verb("earn"), noun("earnings")),
    ),
    chunk(
        id = "put_money_aside",
        text = "put money aside",
        translation = "откладывать деньги",
        headword = "money",
        theme = MONEY,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N + adv",
        example = ex("We put a little money aside every month.", "Каждый месяц мы откладываем немного денег."),
        more = listOf(
            ex("She put money aside for a rainy day.", "Она откладывала деньги на чёрный день."),
            ex("Try to put something aside from each paycheck.", "Старайся откладывать что-то с каждой зарплаты."),
        ),
        collocations = listOf(
            col("build up savings", "накопить сбережения"),
            col("a nest egg", "заначка на будущее"),
        ),
    ),
    chunk(
        id = "live_within_your_means",
        text = "live within your means",
        translation = "жить по средствам",
        headword = "means",
        theme = MONEY,
        level = B2,
        frequencyBand = TOP_5000,
        pattern = "V + prep + poss + N",
        example = ex("They've always lived within their means.", "Они всегда жили по средствам."),
        more = listOf(
            ex(
                "If you live within your means, you won't get into debt.",
                "Если жить по средствам, в долги не влезешь.",
            ),
            ex("He struggles to live within his means.", "Ему тяжело жить по средствам."),
        ),
        error = err(
            "live in your means",
            "Устойчиво: live within your means; «beyond your means» — жить не по средствам.",
        ),
        collocations = listOf(
            col("live beyond your means", "жить не по средствам"),
            col("tighten your belt", "затянуть пояс"),
        ),
    ),
    chunk(
        id = "run_up_a_bill",
        text = "run up a bill",
        translation = "накопить большой счёт",
        headword = "bill",
        theme = MONEY,
        level = B2,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("They ran up a huge bill at the hotel bar.", "В баре отеля они накопили огромный счёт."),
        more = listOf(
            ex("He ran up debts he couldn't pay.", "Он наделал долгов, которые не мог выплатить."),
            ex("Don't run up a big phone bill abroad.", "Не набегай на большой счёт за телефон за границей."),
        ),
        collocations = listOf(
            col("settle a bill", "оплатить счёт"),
            col("foot the bill", "оплатить всё"),
        ),
    ),
    chunk(
        id = "be_ripped_off",
        text = "be ripped off",
        translation = "быть обманутым, переплатить",
        headword = "ripped off",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + V-ed",
        example = ex("Forty euros for a taxi? You were ripped off.", "Сорок евро за такси? Тебя обдурили."),
        more = listOf(
            ex("Tourists often get ripped off here.", "Туристов здесь часто обдирают."),
            ex("I felt ripped off by the repair bill.", "Счёт за ремонт показался мне грабительским."),
        ),
        collocations = listOf(
            col("a rip-off", "грабёж, обдираловка"),
            col("charge over the odds", "брать втридорога"),
        ),
    ),
    chunk(
        id = "set_a_budget",
        text = "set a budget",
        translation = "составить бюджет",
        headword = "budget",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + N",
        example = ex("Set a budget before you go shopping.", "Составь бюджет, прежде чем идти за покупками."),
        more = listOf(
            ex("We set a strict budget for the wedding.", "Мы установили строгий бюджет на свадьбу."),
            ex("The project went way over the set budget.", "Проект сильно вышел за установленный бюджет."),
        ),
        collocations = listOf(
            col("stick to a budget", "придерживаться бюджета"),
            col("blow the budget", "превысить бюджет"),
        ),
    ),
    chunk(
        id = "pay_someone_back",
        text = "pay someone back",
        translation = "вернуть кому-то долг",
        headword = "back",
        theme = MONEY,
        level = A2,
        frequencyBand = TOP_1000,
        pattern = "V + N + adv",
        example = ex("I'll pay you back on Friday.", "Я верну тебе в пятницу."),
        more = listOf(
            ex("He still hasn't paid me back.", "Он до сих пор не вернул мне долг."),
            ex("She paid back every penny.", "Она вернула всё до копейки."),
        ),
        error = err("return you the money back", "Естественнее: pay someone back / pay back the money."),
        collocations = listOf(
            col("owe someone money", "быть должным кому-то"),
            col("lend someone money", "одолжить кому-то денег"),
        ),
    ),
    chunk(
        id = "be_short_of_cash",
        text = "be short of cash",
        translation = "быть на мели",
        headword = "cash",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "be + adj + prep + N",
        example = ex("I'm a bit short of cash this week.", "На этой неделе я немного на мели."),
        more = listOf(
            ex("We were short of cash so we stayed in.", "Денег было в обрез, поэтому мы остались дома."),
            ex("Can you spot me? I'm short of cash.", "Займёшь? Я на мели."),
        ),
        collocations = listOf(
            col("be broke", "быть без денег"),
            col("be flush", "быть при деньгах"),
        ),
    ),
    chunk(
        id = "get_value_for_money",
        text = "get value for money",
        translation = "получить хорошее соотношение цены и качества",
        headword = "value",
        theme = MONEY,
        level = B2,
        frequencyBand = TOP_2000,
        pattern = "V + N + prep + N",
        example = ex("You get real value for money at this restaurant.", "В этом ресторане цена полностью оправдана."),
        more = listOf(
            ex("The cheap ticket was poor value for money.", "Дешёвый билет оказался невыгодным."),
            ex(
                "Customers want value for money, not just a low price.",
                "Клиентам нужно соотношение цены и качества, а не просто низкая цена.",
            ),
        ),
        collocations = listOf(
            col("a bargain", "выгодная покупка"),
            col("worth every penny", "стоит каждой копейки"),
        ),
    ),
    chunk(
        id = "put_it_on_the_card",
        text = "put it on the card",
        translation = "оплатить картой (в кредит)",
        headword = "card",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        register = INFORMAL,
        pattern = "V + pron + prep + N",
        example = ex("I don't have cash — I'll put it on the card.", "Наличных нет — оплачу картой."),
        more = listOf(
            ex("They put the whole holiday on the credit card.", "Весь отпуск они оплатили кредиткой."),
            ex("Try not to put everything on the card.", "Постарайся не вешать всё на карту."),
        ),
        collocations = listOf(
            col("max out a card", "исчерпать лимит по карте"),
            col("pay off the balance", "погасить задолженность по карте"),
        ),
    ),
)
