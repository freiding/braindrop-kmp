package by.freiding.braindrop.feature.vocabulary.data.datasource.seed

import by.freiding.braindrop.feature.vocabulary.domain.model.Chunk
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkLevel.B1
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkRegister.INFORMAL
import by.freiding.braindrop.feature.vocabulary.domain.model.ChunkTheme.FEELINGS
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_1000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_2000
import by.freiding.braindrop.feature.vocabulary.domain.model.FrequencyBand.TOP_5000

/** B1-level content drop for the Feelings theme. */
internal val FEELINGS_CHUNKS_B1: List<Chunk> = listOf(
    chunk(
        id = "let_off_steam",
        text = "let off steam",
        translation = "выпустить пар",
        headword = "steam",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adv + N",
        example = ex("She goes for a run to let off steam.", "Она бегает, чтобы выпустить пар."),
        more = listOf(
            ex("The kids need to let off steam after school.", "После школы детям нужно выпустить энергию."),
            ex("He was just letting off steam — he didn't mean it.", "Он просто выпускал пар, он не всерьёз."),
        ),
        collocations = listOf(
            col("blow off steam", "разрядиться"),
            col("get something off your chest", "выговориться"),
        ),
    ),
    chunk(
        id = "bottle_things_up",
        text = "bottle things up",
        translation = "держать всё в себе",
        headword = "bottle",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + N + adv",
        example = ex("Don't bottle things up — talk to someone.", "Не держи всё в себе — поговори с кем-нибудь."),
        more = listOf(
            ex("He tends to bottle up his feelings.", "Он склонен держать чувства в себе."),
            ex("Bottling it up only makes it worse.", "Держать это в себе — только хуже."),
        ),
        collocations = listOf(
            col("open up to someone", "открыться кому-то"),
            col("keep it to yourself", "держать при себе"),
        ),
    ),
    chunk(
        id = "snap_at_someone",
        text = "snap at someone",
        translation = "огрызнуться на кого-то",
        headword = "snap",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_2000,
        pattern = "V + prep + N",
        example = ex("Sorry I snapped at you — I'm tired.", "Извини, что огрызнулся, я устал."),
        more = listOf(
            ex("She snapped at the waiter for no reason.", "Она без причины рявкнула на официанта."),
            ex("There's no need to snap.", "Незачем срываться."),
        ),
        error = err("snap on someone", "После snap идёт at: snap at someone."),
        collocations = listOf(
            col("bite someone's head off", "накинуться на кого-то"),
            col("take it out on someone", "срываться на ком-то"),
        ),
    ),
    chunk(
        id = "be_down_in_the_dumps",
        text = "be down in the dumps",
        translation = "быть в унынии",
        headword = "dumps",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + idiom",
        example = ex("She's been down in the dumps since the news.", "После той новости она в унынии."),
        more = listOf(
            ex("What's up? You look a bit down in the dumps.", "Что случилось? Ты какой-то поникший."),
            ex("A good film can lift you out of the dumps.", "Хороший фильм может вытащить из хандры."),
        ),
        collocations = listOf(
            col("feel low", "быть не в духе"),
            col("perk up", "воспрянуть духом"),
        ),
    ),
    chunk(
        id = "take_your_mind_off_something",
        text = "take your mind off something",
        translation = "отвлечься от чего-то",
        headword = "mind",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_1000,
        pattern = "V + poss + N + prep + N",
        example = ex("A walk will take your mind off it.", "Прогулка поможет тебе отвлечься."),
        more = listOf(
            ex("She works to take her mind off the worry.", "Она работает, чтобы отвлечься от тревоги."),
            ex("Nothing could take my mind off the exam.", "Ничто не могло отвлечь меня от мыслей об экзамене."),
        ),
        collocations = listOf(
            col("keep yourself busy", "занять себя чем-то"),
            col("a welcome distraction", "приятное отвлечение"),
        ),
    ),
    chunk(
        id = "get_worked_up",
        text = "get worked up about something",
        translation = "накручивать себя из-за чего-то",
        headword = "worked",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "V + adj + prep + N",
        example = ex(
            "Don't get worked up about it — it's not worth it.",
            "Не накручивай себя из-за этого, оно того не стоит.",
        ),
        more = listOf(
            ex("He gets really worked up over politics.", "Он сильно заводится, когда речь о политике."),
            ex("There's no point getting worked up now.", "Нет смысла заводиться сейчас."),
        ),
        collocations = listOf(
            col("wind yourself up", "накрутить себя"),
            col("stay calm", "сохранять спокойствие"),
        ),
    ),
    chunk(
        id = "put_your_mind_at_rest",
        text = "put your mind at rest",
        translation = "успокоить, развеять тревогу",
        headword = "mind",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_5000,
        pattern = "V + poss + N + prep + N",
        example = ex("The test results put my mind at rest.", "Результаты анализов меня успокоили."),
        more = listOf(
            ex("Call her — it'll put your mind at rest.", "Позвони ей — станет спокойнее."),
            ex("Just to put your mind at rest, everything's fine.", "Чтобы ты не переживал: всё в порядке."),
        ),
        collocations = listOf(
            col("set your mind at ease", "успокоить"),
            col("reassure someone", "заверить кого-то"),
        ),
    ),
    chunk(
        id = "be_at_the_end_of_your_tether",
        text = "be at the end of your tether",
        translation = "быть на пределе",
        headword = "tether",
        theme = FEELINGS,
        level = B1,
        frequencyBand = TOP_5000,
        register = INFORMAL,
        pattern = "be + prep + N + prep + poss + N",
        example = ex("By Friday I was at the end of my tether.", "К пятнице я был на пределе."),
        more = listOf(
            ex("She's at the end of her tether with the noise.", "Она уже не может терпеть этот шум."),
            ex("When you're at the end of your tether, take a break.", "Когда ты на пределе, сделай перерыв."),
        ),
        collocations = listOf(
            col("reach breaking point", "дойти до точки"),
            col("have had enough", "быть сытым по горло"),
        ),
    ),
)
