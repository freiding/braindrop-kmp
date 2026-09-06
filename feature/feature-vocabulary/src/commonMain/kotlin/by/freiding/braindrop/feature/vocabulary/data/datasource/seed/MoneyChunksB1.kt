package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.MONEY
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Money theme. */
internal val MONEY_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "put_down_a_deposit",
        text = "put down a deposit",
        translation = "внести залог, первый взнос",
        headword = "deposit",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("We put down a deposit on the flat yesterday.", "Вчера мы внесли залог за квартиру."),
        more = listOf(
            ex("You have to put down a deposit to reserve it.", "Чтобы забронировать, нужно внести задаток."),
            ex("The deposit is refundable if you cancel early.", "Залог возвращается при ранней отмене."),
        ),
        error = err("pay a deposit down for", "«Внести залог» — put down a deposit / pay a deposit."),
        collocations = listOf(
            col("a non-refundable deposit", "невозвратный залог"),
            col("get your deposit back", "вернуть залог"),
        ),
    ),
    chunk(
        id = "take_out_insurance",
        text = "take out insurance",
        translation = "оформить страховку",
        headword = "insurance",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + adv + N",
        example = ex("Always take out travel insurance before a trip.", "Перед поездкой всегда оформляй страховку."),
        more = listOf(
            ex("We took out insurance on the new car.", "Мы застраховали новую машину."),
            ex("The claim was rejected by the insurance company.", "Страховая компания отклонила заявку на выплату."),
        ),
        collocations = listOf(
            col("make a claim", "подать заявку на выплату"),
            col("an insurance premium", "страховой взнос"),
        ),
    ),
    chunk(
        id = "keep_the_receipt",
        text = "keep the receipt",
        translation = "сохранить чек",
        headword = "receipt",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + N",
        example = ex("Keep the receipt in case you want to return it.", "Сохрани чек на случай возврата."),
        more = listOf(
            ex("I can't refund it without a receipt.", "Без чека возврат невозможен."),
            ex("She keeps every receipt for her tax return.", "Она хранит все чеки для налоговой декларации."),
        ),
        error = err("hold the receipt", "«Сохранить чек» — keep the receipt."),
        collocations = listOf(
            col("proof of purchase", "подтверждение покупки"),
            col("an itemised receipt", "детализированный чек"),
        ),
    ),
    chunk(
        id = "be_entitled_to_a_refund",
        text = "be entitled to a refund",
        translation = "иметь право на возврат денег",
        headword = "entitled",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "be + adj + prep + N",
        example = ex(
            "If the item is faulty, you're entitled to a refund.",
            "Если товар бракованный, вы имеете право на возврат денег.",
        ),
        more = listOf(
            ex(
                "Passengers are entitled to compensation for long delays.",
                "При длительных задержках пассажиры имеют право на компенсацию.",
            ),
            ex("Am I entitled to a refund if I change my mind?", "Положен ли мне возврат, если я передумаю?"),
        ),
        error = err("have the right for a refund", "Естественнее: be entitled to a refund / have the right to."),
        collocations = listOf(
            col("claim compensation", "требовать компенсацию"),
            col("a full refund", "полный возврат"),
        ),
    ),
    chunk(
        id = "dip_into_your_savings",
        text = "dip into your savings",
        translation = "залезть в сбережения",
        headword = "savings",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + prep + poss + N",
        example = ex(
            "We had to dip into our savings to fix the roof.",
            "Чтобы починить крышу, пришлось залезть в сбережения.",
        ),
        more = listOf(
            ex("Try not to dip into your savings every month.", "Постарайся не трогать сбережения каждый месяц."),
            ex("He dipped into his savings for the deposit.", "На первый взнос он взял из сбережений."),
        ),
        collocations = listOf(
            col("run down your savings", "истратить сбережения"),
            col("an emergency fund", "финансовая подушка"),
        ),
    ),
    chunk(
        id = "pay_in_instalments",
        text = "pay in instalments",
        translation = "платить в рассрочку",
        headword = "instalments",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + prep + N",
        example = ex("You can pay for the sofa in instalments.", "Диван можно оплатить в рассрочку."),
        more = listOf(
            ex("We're paying it off in monthly instalments.", "Мы выплачиваем это ежемесячными платежами."),
            ex("Interest-free instalments are available.", "Доступна беспроцентная рассрочка."),
        ),
        error = err("pay by parts", "«В рассрочку» — pay in instalments."),
        collocations = listOf(
            col("a monthly instalment", "ежемесячный платёж"),
            col("spread the cost", "разбить оплату"),
        ),
    ),
    chunk(
        id = "come_to_an_arrangement",
        text = "come to an arrangement",
        translation = "договориться, прийти к соглашению",
        headword = "arrangement",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("We came to an arrangement about the rent.", "Мы договорились насчёт аренды."),
        more = listOf(
            ex("The bank came to an arrangement over the debt.", "Банк пошёл на соглашение по долгу."),
            ex("Can't we come to some arrangement?", "Разве мы не можем как-то договориться?"),
        ),
        collocations = listOf(
            col("reach a compromise", "прийти к компромиссу"),
            col("a payment plan", "план платежей"),
        ),
    ),
    chunk(
        id = "tighten_your_belt",
        text = "tighten your belt",
        translation = "затянуть пояс, экономить",
        headword = "belt",
        theme = MONEY,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + poss + N",
        example = ex(
            "Prices went up, so we've had to tighten our belts.",
            "Цены выросли, так что нам пришлось затянуть пояса.",
        ),
        more = listOf(
            ex("Everyone's tightening their belts this year.", "В этом году все экономят."),
            ex("A bit of belt-tightening won't hurt.", "Немного экономии не повредит."),
        ),
        collocations = listOf(
            col("cut back on non-essentials", "урезать необязательные траты"),
            col("live frugally", "жить экономно"),
        ),
    ),
)
