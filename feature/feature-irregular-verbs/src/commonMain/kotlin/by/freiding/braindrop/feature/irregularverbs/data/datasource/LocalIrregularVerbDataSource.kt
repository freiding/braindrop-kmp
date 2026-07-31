package by.freiding.braindrop.feature.irregularverbs.data.datasource

import by.freiding.braindrop.feature.irregularverbs.domain.model.IrregularVerb
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbExample
import by.freiding.braindrop.feature.irregularverbs.domain.model.VerbGroup

class LocalIrregularVerbDataSource {

    fun getVerbs(): List<IrregularVerb> = VERBS

    fun getById(id: String): IrregularVerb? = VERBS.firstOrNull { it.id == id }

    private companion object {
        val VERBS = listOf(
            IrregularVerb("arise", "arise", "arose", "arisen", "возникать, появляться", listOf(
                VerbExample("A problem arose during the meeting.", "Во время встречи возникла проблема."),
                VerbExample("Several questions have arisen.", "Возникло несколько вопросов."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("awake", "awake", "awoke", "awoken", "просыпаться, пробуждаться", listOf(
                VerbExample("She awoke early in the morning.", "Она проснулась рано утром."),
                VerbExample("He had awoken before sunrise.", "Он проснулся до рассвета."),
            ), VerbGroup.ABC_O),
            IrregularVerb("be", "be", "was / were", "been", "быть, являться", listOf(
                VerbExample("She was very tired after work.", "После работы она была очень уставшей."),
                VerbExample("They have been friends for ten years.", "Они дружат уже десять лет."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("bear", "bear", "bore", "borne", "нести; терпеть; рожать", listOf(
                VerbExample("She bore the pain with great courage.", "Она терпела боль с большим мужеством."),
                VerbExample("He has borne the responsibility alone.", "Он нёс ответственность в одиночку."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("beat", "beat", "beat", "beaten", "бить, побеждать", listOf(
                VerbExample("Our team beat them 3 to 1.", "Наша команда победила их со счётом 3:1."),
                VerbExample("The record has been beaten.", "Рекорд был побит."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("become", "become", "became", "become", "становиться, превращаться", listOf(
                VerbExample("She became a doctor at 28.", "Она стала врачом в 28 лет."),
                VerbExample("The weather has become warmer.", "Погода стала теплее."),
            ), VerbGroup.ABA),
            IrregularVerb("begin", "begin", "began", "begun", "начинать(ся)", listOf(
                VerbExample("The lesson began at nine.", "Урок начался в девять."),
                VerbExample("Work has already begun.", "Работа уже началась."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("bend", "bend", "bent", "bent", "гнуть, сгибать", listOf(
                VerbExample("She bent down to pick up the coin.", "Она нагнулась, чтобы поднять монету."),
                VerbExample("The pipes had bent under the pressure.", "Трубы согнулись под давлением."),
            ), VerbGroup.ABB_T),
            IrregularVerb("bet", "bet", "bet", "bet", "держать пари, ставить", listOf(
                VerbExample("He bet twenty dollars on the race.", "Он поставил двадцать долларов на гонку."),
                VerbExample("I bet you can't do it in one minute.", "Спорю, ты не сделаешь это за минуту."),
            ), VerbGroup.AAA),
            IrregularVerb("bind", "bind", "bound", "bound", "связывать, переплетать", listOf(
                VerbExample("They bound the prisoner's hands.", "Они связали руки заключённому."),
                VerbExample("The contract bound both parties.", "Контракт связывал обе стороны."),
            ), VerbGroup.ABB_OUND),
            IrregularVerb("bite", "bite", "bit", "bitten", "кусать(ся)", listOf(
                VerbExample("The dog bit the postman.", "Собака укусила почтальона."),
                VerbExample("He had been bitten by a snake.", "Его укусила змея."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("bleed", "bleed", "bled", "bled", "кровоточить", listOf(
                VerbExample("His hand bled badly after the cut.", "После пореза его рука сильно кровоточила."),
                VerbExample("The wound has stopped bleeding.", "Рана перестала кровоточить."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("blow", "blow", "blew", "blown", "дуть, взрывать", listOf(
                VerbExample("The wind blew all night long.", "Ветер дул всю ночь."),
                VerbExample("The bridge had been blown up.", "Мост был взорван."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("break", "break", "broke", "broken", "ломать, нарушать", listOf(
                VerbExample("He broke his leg skiing.", "Он сломал ногу на лыжах."),
                VerbExample("The window has been broken.", "Окно было разбито."),
            ), VerbGroup.ABC_O),
            IrregularVerb("breed", "breed", "bred", "bred", "разводить, выводить", listOf(
                VerbExample("They bred horses on their farm.", "Они разводили лошадей на своей ферме."),
                VerbExample("These dogs were bred for hunting.", "Эти собаки были выведены для охоты."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("bring", "bring", "brought", "brought", "приносить, привозить", listOf(
                VerbExample("Please bring me a glass of water.", "Пожалуйста, принеси мне стакан воды."),
                VerbExample("She brought her laptop to the office.", "Она принесла ноутбук в офис."),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("broadcast", "broadcast", "broadcast", "broadcast", "транслировать, вещать", listOf(
                VerbExample("The match was broadcast live.", "Матч транслировался в прямом эфире."),
                VerbExample("The news was broadcast at eight.", "Новости вышли в эфир в восемь."),
            ), VerbGroup.AAA),
            IrregularVerb("build", "build", "built", "built", "строить, создавать", listOf(
                VerbExample("They built a new bridge last year.", "В прошлом году они построили новый мост."),
                VerbExample("This house was built in 1920.", "Этот дом был построен в 1920 году."),
            ), VerbGroup.ABB_T),
            IrregularVerb("burn", "burn", "burnt", "burnt", "гореть, жечь", listOf(
                VerbExample("The candle burnt all night.", "Свеча горела всю ночь."),
                VerbExample("The old documents had been burnt.", "Старые документы были сожжены."),
            ), VerbGroup.ABB_T),
            IrregularVerb("burst", "burst", "burst", "burst", "взрываться, лопаться", listOf(
                VerbExample("The pipe burst due to the cold.", "Труба лопнула из-за мороза."),
                VerbExample("She burst into tears.", "Она расплакалась."),
            ), VerbGroup.AAA),
            IrregularVerb("buy", "buy", "bought", "bought", "покупать", listOf(
                VerbExample("He bought a new car last month.", "В прошлом месяце он купил новую машину."),
                VerbExample("Have you bought the tickets yet?", "Ты уже купил билеты?"),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("cast", "cast", "cast", "cast", "бросать; отливать; назначать (роли)", listOf(
                VerbExample("He cast the fishing line into the river.", "Он забросил удочку в реку."),
                VerbExample("She was cast as the lead role.", "Её назначили на главную роль."),
            ), VerbGroup.AAA),
            IrregularVerb("catch", "catch", "caught", "caught", "ловить, поймать", listOf(
                VerbExample("She caught the ball easily.", "Она легко поймала мяч."),
                VerbExample("Have you caught a cold?", "Ты простудился?"),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("choose", "choose", "chose", "chosen", "выбирать", listOf(
                VerbExample("He chose the red shirt.", "Он выбрал красную рубашку."),
                VerbExample("She has chosen to study medicine.", "Она решила изучать медицину."),
            ), VerbGroup.ABC_O),
            IrregularVerb("cling", "cling", "clung", "clung", "цепляться, прижиматься", listOf(
                VerbExample("The child clung to her mother.", "Ребёнок прижался к матери."),
                VerbExample("Wet clothes clung to his skin.", "Мокрая одежда прилипла к его коже."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("come", "come", "came", "come", "приходить, приезжать", listOf(
                VerbExample("She came home at midnight.", "Она вернулась домой в полночь."),
                VerbExample("He has come a long way.", "Он прошёл долгий путь."),
            ), VerbGroup.ABA),
            IrregularVerb("cost", "cost", "cost", "cost", "стоить", listOf(
                VerbExample("The coat cost three hundred dollars.", "Пальто стоило триста долларов."),
                VerbExample("How much has it cost in total?", "Сколько это стоило в общей сложности?"),
            ), VerbGroup.AAA),
            IrregularVerb("creep", "creep", "crept", "crept", "ползти, красться", listOf(
                VerbExample("The cat crept silently towards the bird.", "Кошка бесшумно подкрадывалась к птице."),
                VerbExample("Doubt had crept into his mind.", "Сомнение закралось в его душу."),
            ), VerbGroup.ABB_T),
            IrregularVerb("cut", "cut", "cut", "cut", "резать, стричь", listOf(
                VerbExample("She cut the cake into eight pieces.", "Она разрезала торт на восемь кусков."),
                VerbExample("He has cut his hair short.", "Он постригся коротко."),
            ), VerbGroup.AAA),
            IrregularVerb("deal", "deal", "dealt", "dealt", "иметь дело; раздавать", listOf(
                VerbExample("They dealt with the problem immediately.", "Они немедленно решили проблему."),
                VerbExample("Cards had been dealt to all players.", "Карты были розданы всем игрокам."),
            ), VerbGroup.ABB_T),
            IrregularVerb("dig", "dig", "dug", "dug", "копать", listOf(
                VerbExample("The dog dug a hole in the garden.", "Собака вырыла яму в саду."),
                VerbExample("They had dug a tunnel under the wall.", "Они вырыли тоннель под стеной."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("do", "do", "did", "done", "делать", listOf(
                VerbExample("She did her homework before dinner.", "Она сделала домашнее задание до ужина."),
                VerbExample("What have you done?", "Что ты наделал?"),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("draw", "draw", "drew", "drawn", "рисовать; тянуть; ничья", listOf(
                VerbExample("He drew a map of the city.", "Он нарисовал карту города."),
                VerbExample("The match ended in a draw.", "Матч закончился вничью."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("dream", "dream", "dreamt", "dreamt", "мечтать; видеть сон", listOf(
                VerbExample("She dreamt about flying.", "Ей приснилось, что она летит."),
                VerbExample("He has always dreamt of travelling the world.", "Он всегда мечтал путешествовать по миру."),
            ), VerbGroup.ABB_T),
            IrregularVerb("drink", "drink", "drank", "drunk", "пить", listOf(
                VerbExample("He drank two cups of coffee this morning.", "Сегодня утром он выпил две чашки кофе."),
                VerbExample("She has never drunk alcohol.", "Она никогда не пила алкоголь."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("drive", "drive", "drove", "driven", "ехать (на машине), вести", listOf(
                VerbExample("She drove to work every day.", "Каждый день она ездила на работу на машине."),
                VerbExample("Have you ever driven a truck?", "Ты когда-нибудь водил грузовик?"),
            ), VerbGroup.ABC_O),
            IrregularVerb("eat", "eat", "ate", "eaten", "есть, кушать", listOf(
                VerbExample("They ate pizza for dinner.", "На ужин они ели пиццу."),
                VerbExample("Have you eaten anything today?", "Ты сегодня что-нибудь ел?"),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("fall", "fall", "fell", "fallen", "падать", listOf(
                VerbExample("The leaves fell from the trees.", "Листья упали с деревьев."),
                VerbExample("She has fallen asleep on the couch.", "Она заснула на диване."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("feed", "feed", "fed", "fed", "кормить", listOf(
                VerbExample("She fed the baby every three hours.", "Она кормила ребёнка каждые три часа."),
                VerbExample("Have you fed the cat?", "Ты покормил кошку?"),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("feel", "feel", "felt", "felt", "чувствовать", listOf(
                VerbExample("I felt nervous before the exam.", "Я нервничал перед экзаменом."),
                VerbExample("She has felt much better since yesterday.", "Она чувствует себя намного лучше со вчерашнего дня."),
            ), VerbGroup.ABB_T),
            IrregularVerb("fight", "fight", "fought", "fought", "бороться, воевать, драться", listOf(
                VerbExample("They fought bravely in the war.", "Они храбро сражались на войне."),
                VerbExample("She has always fought for justice.", "Она всегда боролась за справедливость."),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("find", "find", "found", "found", "находить, обнаруживать", listOf(
                VerbExample("He found his keys under the sofa.", "Он нашёл ключи под диваном."),
                VerbExample("Have you found a new apartment?", "Ты нашёл новую квартиру?"),
            ), VerbGroup.ABB_OUND),
            IrregularVerb("flee", "flee", "fled", "fled", "бежать, спасаться бегством", listOf(
                VerbExample("The thief fled from the police.", "Вор сбежал от полиции."),
                VerbExample("Many people had fled the country.", "Многие люди покинули страну."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("fling", "fling", "flung", "flung", "бросать, швырять", listOf(
                VerbExample("She flung her bag onto the bed.", "Она бросила сумку на кровать."),
                VerbExample("He had flung the door open.", "Он распахнул дверь."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("fly", "fly", "flew", "flown", "летать, лететь", listOf(
                VerbExample("They flew to Paris for the weekend.", "На выходные они полетели в Париж."),
                VerbExample("She has never flown before.", "Она никогда раньше не летала."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("forbid", "forbid", "forbade", "forbidden", "запрещать", listOf(
                VerbExample("The teacher forbade phones in class.", "Учитель запретил телефоны на уроке."),
                VerbExample("Smoking is forbidden here.", "Здесь запрещено курить."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("foresee", "foresee", "foresaw", "foreseen", "предвидеть", listOf(
                VerbExample("Nobody foresaw such a crisis.", "Никто не предвидел такого кризиса."),
                VerbExample("The risks had not been foreseen.", "Риски не были предусмотрены."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("forget", "forget", "forgot", "forgotten", "забывать", listOf(
                VerbExample("She forgot to call him back.", "Она забыла ему перезвонить."),
                VerbExample("I've completely forgotten his name.", "Я совершенно забыл его имя."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("forgive", "forgive", "forgave", "forgiven", "прощать", listOf(
                VerbExample("She forgave him for his mistake.", "Она простила его за ошибку."),
                VerbExample("Have you forgiven him?", "Ты его простил?"),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("forsake", "forsake", "forsook", "forsaken", "покидать, отрекаться", listOf(
                VerbExample("He forsook his old habits.", "Он отказался от старых привычек."),
                VerbExample("She felt forsaken by her friends.", "Она чувствовала себя брошенной друзьями."),
            ), VerbGroup.ABC_O),
            IrregularVerb("freeze", "freeze", "froze", "frozen", "замерзать, замораживать", listOf(
                VerbExample("The river froze over in January.", "Река замёрзла в январе."),
                VerbExample("The pipes have frozen.", "Трубы замёрзли."),
            ), VerbGroup.ABC_O),
            IrregularVerb("get", "get", "got", "got", "получать, становиться", listOf(
                VerbExample("She got a promotion last month.", "В прошлом месяце она получила повышение."),
                VerbExample("Have you got the message?", "Ты получил сообщение?"),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("give", "give", "gave", "given", "давать, дарить", listOf(
                VerbExample("He gave her a birthday present.", "Он подарил ей подарок на день рождения."),
                VerbExample("She has given me good advice.", "Она дала мне хороший совет."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("go", "go", "went", "gone", "идти, ехать", listOf(
                VerbExample("We went to the cinema last night.", "Вчера вечером мы ходили в кино."),
                VerbExample("Has he gone home already?", "Он уже ушёл домой?"),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("grind", "grind", "ground", "ground", "молоть, шлифовать", listOf(
                VerbExample("She ground the coffee beans.", "Она смолола кофейные зёрна."),
                VerbExample("The knife had been ground sharp.", "Нож был заточен до остроты."),
            ), VerbGroup.ABB_OUND),
            IrregularVerb("grow", "grow", "grew", "grown", "расти, выращивать", listOf(
                VerbExample("He grew vegetables in his garden.", "Он выращивал овощи в саду."),
                VerbExample("The city has grown a lot.", "Город сильно вырос."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("hang", "hang", "hung", "hung", "вешать, висеть", listOf(
                VerbExample("She hung the painting on the wall.", "Она повесила картину на стену."),
                VerbExample("The coat has hung there for days.", "Пальто висит там уже несколько дней."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("have", "have", "had", "had", "иметь; вспомогательный глагол", listOf(
                VerbExample("She had a meeting at ten.", "У неё было совещание в десять."),
                VerbExample("He has had a difficult week.", "У него была тяжёлая неделя."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("hear", "hear", "heard", "heard", "слышать", listOf(
                VerbExample("I heard a strange noise outside.", "Я услышал странный шум снаружи."),
                VerbExample("Have you heard the latest news?", "Ты слышал последние новости?"),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("hide", "hide", "hid", "hidden", "прятать(ся)", listOf(
                VerbExample("The children hid under the bed.", "Дети спрятались под кроватью."),
                VerbExample("The treasure was hidden in the cave.", "Клад был спрятан в пещере."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("hit", "hit", "hit", "hit", "бить, ударять, попадать", listOf(
                VerbExample("He hit the ball out of the park.", "Он выбил мяч за пределы поля."),
                VerbExample("The car has hit a tree.", "Машина врезалась в дерево."),
            ), VerbGroup.AAA),
            IrregularVerb("hold", "hold", "held", "held", "держать, проводить", listOf(
                VerbExample("She held the baby carefully.", "Она осторожно держала ребёнка."),
                VerbExample("The meeting will be held on Monday.", "Встреча состоится в понедельник."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("hurt", "hurt", "hurt", "hurt", "причинять боль, болеть", listOf(
                VerbExample("He hurt his knee while running.", "Он ушиб колено во время бега."),
                VerbExample("Does it still hurt?", "Всё ещё болит?"),
            ), VerbGroup.AAA),
            IrregularVerb("input", "input", "input", "input", "вводить (данные)", listOf(
                VerbExample("She input all the data into the system.", "Она ввела все данные в систему."),
                VerbExample("The values have been input correctly.", "Значения были введены правильно."),
            ), VerbGroup.AAA),
            IrregularVerb("keep", "keep", "kept", "kept", "хранить, держать, продолжать", listOf(
                VerbExample("She kept all his letters.", "Она хранила все его письма."),
                VerbExample("He has kept his promise.", "Он сдержал своё обещание."),
            ), VerbGroup.ABB_T),
            IrregularVerb("kneel", "kneel", "knelt", "knelt", "стоять на коленях", listOf(
                VerbExample("He knelt down to propose.", "Он встал на колено, чтобы сделать предложение."),
                VerbExample("She had knelt in prayer.", "Она стояла на коленях в молитве."),
            ), VerbGroup.ABB_T),
            IrregularVerb("knit", "knit", "knit", "knit", "вязать", listOf(
                VerbExample("She knit a scarf for winter.", "Она связала шарф на зиму."),
                VerbExample("The wound had knit well.", "Рана хорошо зажила."),
            ), VerbGroup.AAA),
            IrregularVerb("know", "know", "knew", "known", "знать", listOf(
                VerbExample("She knew the answer immediately.", "Она сразу знала ответ."),
                VerbExample("Have you known him for long?", "Вы давно знакомы?"),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("lay", "lay", "laid", "laid", "класть, укладывать", listOf(
                VerbExample("She laid the book on the table.", "Она положила книгу на стол."),
                VerbExample("The foundation had been laid.", "Фундамент был заложен."),
            ), VerbGroup.ABB_AID),
            IrregularVerb("lead", "lead", "led", "led", "вести, руководить", listOf(
                VerbExample("She led the team to victory.", "Она привела команду к победе."),
                VerbExample("This path has led us nowhere.", "Этот путь никуда нас не привёл."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("lean", "lean", "leant", "leant", "наклоняться, опираться", listOf(
                VerbExample("He leant against the wall.", "Он прислонился к стене."),
                VerbExample("She had leant forward to hear better.", "Она наклонилась вперёд, чтобы лучше слышать."),
            ), VerbGroup.ABB_T),
            IrregularVerb("leap", "leap", "leapt", "leapt", "прыгать, перепрыгивать", listOf(
                VerbExample("The cat leapt onto the table.", "Кошка запрыгнула на стол."),
                VerbExample("He has leapt at the opportunity.", "Он ухватился за возможность."),
            ), VerbGroup.ABB_T),
            IrregularVerb("learn", "learn", "learnt", "learnt", "учиться, узнавать", listOf(
                VerbExample("She learnt to drive last summer.", "Прошлым летом она научилась водить."),
                VerbExample("I've learnt a lot from this course.", "Я многому научился на этом курсе."),
            ), VerbGroup.ABB_T),
            IrregularVerb("leave", "leave", "left", "left", "уходить, оставлять", listOf(
                VerbExample("He left the office at six.", "Он ушёл из офиса в шесть."),
                VerbExample("Has she left already?", "Она уже ушла?"),
            ), VerbGroup.ABB_T),
            IrregularVerb("lend", "lend", "lent", "lent", "давать взаймы, одалживать", listOf(
                VerbExample("He lent me his car for the weekend.", "Он одолжил мне машину на выходные."),
                VerbExample("She has lent him money before.", "Раньше она одалживала ему деньги."),
            ), VerbGroup.ABB_T),
            IrregularVerb("let", "let", "let", "let", "позволять, разрешать", listOf(
                VerbExample("She let him borrow her pen.", "Она разрешила ему взять её ручку."),
                VerbExample("Don't let the opportunity pass.", "Не упускай возможность."),
            ), VerbGroup.AAA),
            IrregularVerb("lie", "lie", "lay", "lain", "лежать", listOf(
                VerbExample("He lay on the grass and looked at the sky.", "Он лежал на траве и смотрел на небо."),
                VerbExample("The book had lain on the shelf for years.", "Книга годами лежала на полке."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("light", "light", "lit", "lit", "зажигать, освещать", listOf(
                VerbExample("She lit a candle for the dinner.", "Она зажгла свечу к ужину."),
                VerbExample("The room was lit by a single lamp.", "Комната была освещена одной лампой."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("lose", "lose", "lost", "lost", "терять, проигрывать", listOf(
                VerbExample("He lost his wallet on the bus.", "Он потерял кошелёк в автобусе."),
                VerbExample("We have never lost a game this season.", "В этом сезоне мы не проиграли ни одной игры."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("make", "make", "made", "made", "делать, создавать", listOf(
                VerbExample("She made a delicious cake.", "Она испекла вкусный торт."),
                VerbExample("Have you made a decision yet?", "Ты уже принял решение?"),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("mean", "mean", "meant", "meant", "значить, иметь в виду", listOf(
                VerbExample("What did you mean by that?", "Что ты имел в виду?"),
                VerbExample("She didn't mean to hurt him.", "Она не хотела его обидеть."),
            ), VerbGroup.ABB_T),
            IrregularVerb("meet", "meet", "met", "met", "встречать(ся)", listOf(
                VerbExample("We met at a coffee shop downtown.", "Мы встретились в кофейне в центре города."),
                VerbExample("Have you met my sister?", "Ты знаком с моей сестрой?"),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("mislead", "mislead", "misled", "misled", "вводить в заблуждение", listOf(
                VerbExample("The advertisement misled consumers.", "Реклама ввела потребителей в заблуждение."),
                VerbExample("We were misled by false information.", "Нас ввела в заблуждение ложная информация."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("mistake", "mistake", "mistook", "mistaken", "ошибаться, принять за", listOf(
                VerbExample("I mistook him for his brother.", "Я принял его за его брата."),
                VerbExample("She was mistaken about the date.", "Она ошиблась насчёт даты."),
            ), VerbGroup.ABC_O),
            IrregularVerb("misunderstand", "misunderstand", "misunderstood", "misunderstood", "неправильно понять", listOf(
                VerbExample("He misunderstood my question.", "Он неправильно понял мой вопрос."),
                VerbExample("I think you have misunderstood the situation.", "Думаю, вы неправильно поняли ситуацию."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("outdo", "outdo", "outdid", "outdone", "превосходить, превзойти", listOf(
                VerbExample("She outdid herself this time.", "На этот раз она превзошла саму себя."),
                VerbExample("He has been outdone by his rival.", "Его соперник его превзошёл."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("outgrow", "outgrow", "outgrew", "outgrown", "перерастать, становиться большим", listOf(
                VerbExample("He outgrew his shoes in a month.", "Он вырос из ботинок за месяц."),
                VerbExample("She has outgrown her childhood fears.", "Она избавилась от детских страхов."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("outrun", "outrun", "outran", "outrun", "обгонять, перегонять", listOf(
                VerbExample("She outran all the other competitors.", "Она обогнала всех остальных участников."),
                VerbExample("The cheetah has outrun the deer.", "Гепард обогнал оленя."),
            ), VerbGroup.ABA),
            IrregularVerb("outshine", "outshine", "outshone", "outshone", "затмевать, превосходить", listOf(
                VerbExample("Her performance outshone all others.", "Её выступление затмило все остальные."),
                VerbExample("He has outshone his colleagues.", "Он превзошёл своих коллег."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("overcome", "overcome", "overcame", "overcome", "преодолевать, побеждать", listOf(
                VerbExample("She overcame her fear of heights.", "Она преодолела страх высоты."),
                VerbExample("Many obstacles have been overcome.", "Многие препятствия были преодолены."),
            ), VerbGroup.ABA),
            IrregularVerb("overdo", "overdo", "overdid", "overdone", "переусердствовать", listOf(
                VerbExample("Don't overdo the exercise on the first day.", "Не переусердствуй с упражнениями в первый день."),
                VerbExample("The steak was overdone.", "Стейк был пережарен."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("override", "override", "overrode", "overridden", "отменять, игнорировать", listOf(
                VerbExample("The manager overrode the decision.", "Менеджер отменил решение."),
                VerbExample("The veto has been overridden.", "Вето было преодолено."),
            ), VerbGroup.ABC_O),
            IrregularVerb("oversee", "oversee", "oversaw", "overseen", "надзирать, наблюдать", listOf(
                VerbExample("She oversaw the construction project.", "Она руководила строительным проектом."),
                VerbExample("The work was overseen by an expert.", "Работу контролировал эксперт."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("oversleep", "oversleep", "overslept", "overslept", "проспать", listOf(
                VerbExample("He overslept and missed his train.", "Он проспал и опоздал на поезд."),
                VerbExample("She has overslept again.", "Она снова проспала."),
            ), VerbGroup.ABB_T),
            IrregularVerb("overtake", "overtake", "overtook", "overtaken", "обгонять, застать врасплох", listOf(
                VerbExample("He overtook the slow car on the highway.", "Он обогнал медленную машину на шоссе."),
                VerbExample("The leading runner was overtaken at the finish.", "Лидер был обогнан на финише."),
            ), VerbGroup.ABC_O),
            IrregularVerb("overpay", "overpay", "overpaid", "overpaid", "переплачивать", listOf(
                VerbExample("We overpaid for the hotel room.", "Мы переплатили за номер в отеле."),
                VerbExample("The contractor was overpaid.", "Подрядчику переплатили."),
            ), VerbGroup.ABB_AID),
            IrregularVerb("partake", "partake", "partook", "partaken", "участвовать, принимать участие", listOf(
                VerbExample("Everyone partook in the celebration.", "Все приняли участие в праздновании."),
                VerbExample("Have you partaken of the meal?", "Вы отведали угощение?"),
            ), VerbGroup.ABC_O),
            IrregularVerb("pay", "pay", "paid", "paid", "платить", listOf(
                VerbExample("She paid for the meal.", "Она заплатила за еду."),
                VerbExample("Have you paid the rent yet?", "Ты уже заплатил за аренду?"),
            ), VerbGroup.ABB_AID),
            IrregularVerb("prove", "prove", "proved", "proven", "доказывать", listOf(
                VerbExample("He proved his innocence.", "Он доказал свою невиновность."),
                VerbExample("The theory has been proven correct.", "Теория была доказана."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("put", "put", "put", "put", "класть, ставить", listOf(
                VerbExample("She put the keys on the table.", "Она положила ключи на стол."),
                VerbExample("Where have you put my glasses?", "Куда ты положил мои очки?"),
            ), VerbGroup.AAA),
            IrregularVerb("quit", "quit", "quit", "quit", "бросать, уходить (с работы)", listOf(
                VerbExample("He quit his job last Friday.", "В прошлую пятницу он уволился."),
                VerbExample("She has quit smoking.", "Она бросила курить."),
            ), VerbGroup.AAA),
            IrregularVerb("read", "read", "read", "read", "читать", listOf(
                VerbExample("He read the whole book in one night.", "Он прочитал всю книгу за одну ночь."),
                VerbExample("Have you read this article?", "Ты читал эту статью?"),
            ), VerbGroup.AAA),
            IrregularVerb("rebuild", "rebuild", "rebuilt", "rebuilt", "перестраивать, восстанавливать", listOf(
                VerbExample("They rebuilt the town after the earthquake.", "После землетрясения они восстановили город."),
                VerbExample("The bridge has been rebuilt.", "Мост был перестроен."),
            ), VerbGroup.ABB_T),
            IrregularVerb("redo", "redo", "redid", "redone", "переделывать", listOf(
                VerbExample("She redid the essay from scratch.", "Она переписала сочинение с нуля."),
                VerbExample("The design has been redone.", "Дизайн был переработан."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("rewrite", "rewrite", "rewrote", "rewritten", "переписывать", listOf(
                VerbExample("He rewrote the report three times.", "Он переписывал доклад три раза."),
                VerbExample("The article has been completely rewritten.", "Статья была полностью переписана."),
            ), VerbGroup.ABC_O),
            IrregularVerb("rid", "rid", "rid", "rid", "избавляться (от чего-либо)", listOf(
                VerbExample("She finally rid herself of the problem.", "Наконец она избавилась от проблемы."),
                VerbExample("The house has been rid of pests.", "Дом был очищен от вредителей."),
            ), VerbGroup.AAA),
            IrregularVerb("ride", "ride", "rode", "ridden", "ездить верхом; кататься", listOf(
                VerbExample("He rode his bicycle to school.", "Он поехал в школу на велосипеде."),
                VerbExample("Have you ever ridden a horse?", "Ты когда-нибудь ездил верхом?"),
            ), VerbGroup.ABC_O),
            IrregularVerb("ring", "ring", "rang", "rung", "звонить, звенеть", listOf(
                VerbExample("The phone rang three times.", "Телефон позвонил три раза."),
                VerbExample("Has the bell rung yet?", "Звонок уже прозвенел?"),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("rise", "rise", "rose", "risen", "подниматься, вставать", listOf(
                VerbExample("The sun rose at six this morning.", "Сегодня утром солнце взошло в шесть."),
                VerbExample("Prices have risen sharply.", "Цены резко выросли."),
            ), VerbGroup.ABC_O),
            IrregularVerb("run", "run", "ran", "run", "бегать, бежать", listOf(
                VerbExample("She ran a marathon last spring.", "Прошлой весной она пробежала марафон."),
                VerbExample("How long have you been running?", "Как давно ты занимаешься бегом?"),
            ), VerbGroup.ABA),
            IrregularVerb("say", "say", "said", "said", "говорить, сказать", listOf(
                VerbExample("He said he would be here by noon.", "Он сказал, что будет здесь к полудню."),
                VerbExample("What did she say?", "Что она сказала?"),
            ), VerbGroup.ABB_AID),
            IrregularVerb("see", "see", "saw", "seen", "видеть", listOf(
                VerbExample("I saw her at the supermarket.", "Я видел её в супермаркете."),
                VerbExample("Have you seen this film?", "Ты видел этот фильм?"),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("seek", "seek", "sought", "sought", "искать, стремиться", listOf(
                VerbExample("She sought help from a doctor.", "Она обратилась за помощью к врачу."),
                VerbExample("A solution has been sought for months.", "Решение искали несколько месяцев."),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("sell", "sell", "sold", "sold", "продавать", listOf(
                VerbExample("He sold his car to buy a motorbike.", "Он продал машину, чтобы купить мотоцикл."),
                VerbExample("All tickets have been sold.", "Все билеты проданы."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("send", "send", "sent", "sent", "посылать, отправлять", listOf(
                VerbExample("She sent him a long letter.", "Она написала ему длинное письмо."),
                VerbExample("Has the package been sent?", "Посылка была отправлена?"),
            ), VerbGroup.ABB_T),
            IrregularVerb("set", "set", "set", "set", "ставить, устанавливать; заходить (о солнце)", listOf(
                VerbExample("She set the alarm for seven o'clock.", "Она поставила будильник на семь."),
                VerbExample("The sun had set by the time we arrived.", "Когда мы приехали, солнце уже зашло."),
            ), VerbGroup.AAA),
            IrregularVerb("sew", "sew", "sewed", "sewn", "шить", listOf(
                VerbExample("She sewed the button back on.", "Она пришила пуговицу обратно."),
                VerbExample("The dress had been sewn by hand.", "Платье было сшито вручную."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("shake", "shake", "shook", "shaken", "трясти, пожимать (руку)", listOf(
                VerbExample("They shook hands and signed the deal.", "Они пожали руки и подписали сделку."),
                VerbExample("She was shaken by the news.", "Новость потрясла её."),
            ), VerbGroup.ABC_O),
            IrregularVerb("shed", "shed", "shed", "shed", "проливать, сбрасывать", listOf(
                VerbExample("She shed tears at the farewell.", "Она пролила слёзы на прощание."),
                VerbExample("The trees have shed their leaves.", "Деревья сбросили листья."),
            ), VerbGroup.AAA),
            IrregularVerb("shine", "shine", "shone", "shone", "светить, сиять", listOf(
                VerbExample("The sun shone brightly all day.", "Солнце ярко светило весь день."),
                VerbExample("Her eyes shone with excitement.", "Её глаза сияли от волнения."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("shoot", "shoot", "shot", "shot", "стрелять", listOf(
                VerbExample("The goalkeeper shot the ball far.", "Вратарь далеко пробил по мячу."),
                VerbExample("The film was shot in New Zealand.", "Фильм снимали в Новой Зеландии."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("show", "show", "showed", "shown", "показывать", listOf(
                VerbExample("He showed me the way to the station.", "Он показал мне дорогу на станцию."),
                VerbExample("She has shown great talent.", "Она проявила большой талант."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("shrink", "shrink", "shrank", "shrunk", "сжиматься, уменьшать", listOf(
                VerbExample("The jumper shrank in the wash.", "Свитер сел после стирки."),
                VerbExample("The budget has shrunk significantly.", "Бюджет значительно сократился."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("shut", "shut", "shut", "shut", "закрывать, захлопывать", listOf(
                VerbExample("She shut the window before leaving.", "Она закрыла окно перед уходом."),
                VerbExample("The shop has shut early today.", "Сегодня магазин закрылся раньше."),
            ), VerbGroup.AAA),
            IrregularVerb("sing", "sing", "sang", "sung", "петь", listOf(
                VerbExample("She sang beautifully at the concert.", "Она красиво пела на концерте."),
                VerbExample("This song has been sung for generations.", "Эта песня передаётся из поколения в поколение."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("sink", "sink", "sank", "sunk", "тонуть, опускаться", listOf(
                VerbExample("The ship sank within minutes.", "Корабль затонул за несколько минут."),
                VerbExample("Her heart sank when she heard the news.", "Её сердце упало, когда она услышала новость."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("sit", "sit", "sat", "sat", "сидеть", listOf(
                VerbExample("She sat by the window and read.", "Она сидела у окна и читала."),
                VerbExample("We have sat here for two hours.", "Мы сидим здесь уже два часа."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("sleep", "sleep", "slept", "slept", "спать", listOf(
                VerbExample("He slept for ten hours straight.", "Он спал десять часов подряд."),
                VerbExample("Have you slept well?", "Ты хорошо выспался?"),
            ), VerbGroup.ABB_T),
            IrregularVerb("slide", "slide", "slid", "slid", "скользить", listOf(
                VerbExample("The children slid down the icy hill.", "Дети катились вниз по заледеневшему склону."),
                VerbExample("The drawer had slid open.", "Ящик выдвинулся."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("sling", "sling", "slung", "slung", "бросать; вешать (через плечо)", listOf(
                VerbExample("He slung his bag over his shoulder.", "Он перекинул сумку через плечо."),
                VerbExample("The hammock was slung between two trees.", "Гамак был натянут между двумя деревьями."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("smell", "smell", "smelt", "smelt", "пахнуть, нюхать", listOf(
                VerbExample("The flowers smelt wonderful.", "Цветы пахли чудесно."),
                VerbExample("Something has smelt strange all day.", "Весь день что-то странно пахнет."),
            ), VerbGroup.ABB_T),
            IrregularVerb("sow", "sow", "sowed", "sown", "сеять, засевать", listOf(
                VerbExample("The farmer sowed wheat in autumn.", "Осенью фермер засеял пшеницу."),
                VerbExample("Seeds have been sown in the garden.", "В саду посеяны семена."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("speak", "speak", "spoke", "spoken", "говорить, разговаривать", listOf(
                VerbExample("She spoke to him about the problem.", "Она поговорила с ним о проблеме."),
                VerbExample("Have you ever spoken in public?", "Ты когда-нибудь выступал публично?"),
            ), VerbGroup.ABC_O),
            IrregularVerb("speed", "speed", "sped", "sped", "мчаться, ускорять", listOf(
                VerbExample("The car sped down the motorway.", "Машина мчалась по автомагистрали."),
                VerbExample("The project has sped up considerably.", "Проект значительно ускорился."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("spell", "spell", "spelt", "spelt", "писать/произносить по буквам", listOf(
                VerbExample("Could you spell your name, please?", "Не могли бы вы произнести своё имя по буквам?"),
                VerbExample("The word was spelt incorrectly.", "Слово было написано неправильно."),
            ), VerbGroup.ABB_T),
            IrregularVerb("spend", "spend", "spent", "spent", "тратить, проводить (время)", listOf(
                VerbExample("She spent all her savings on a trip.", "Она потратила все сбережения на поездку."),
                VerbExample("How much have you spent today?", "Сколько ты потратил сегодня?"),
            ), VerbGroup.ABB_T),
            IrregularVerb("spill", "spill", "spilt", "spilt", "проливать, рассыпать", listOf(
                VerbExample("He spilt coffee on his shirt.", "Он пролил кофе на рубашку."),
                VerbExample("The milk has been spilt.", "Молоко было пролито."),
            ), VerbGroup.ABB_T),
            IrregularVerb("spin", "spin", "spun", "spun", "крутиться, вращаться", listOf(
                VerbExample("The wheel spun fast.", "Колесо быстро вращалось."),
                VerbExample("She spun a story about her adventures.", "Она рассказала историю о своих приключениях."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("spit", "spit", "spat", "spat", "плевать", listOf(
                VerbExample("He spat out the bitter medicine.", "Он выплюнул горькое лекарство."),
                VerbExample("The volcano has spat out lava.", "Вулкан извергал лаву."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("split", "split", "split", "split", "расщеплять, делить", listOf(
                VerbExample("They split the bill equally.", "Они разделили счёт поровну."),
                VerbExample("The group has split into two teams.", "Группа разделилась на две команды."),
            ), VerbGroup.AAA),
            IrregularVerb("spread", "spread", "spread", "spread", "распространять(ся), намазывать", listOf(
                VerbExample("The news spread quickly across the city.", "Новость быстро распространилась по городу."),
                VerbExample("She spread butter on the toast.", "Она намазала масло на тост."),
            ), VerbGroup.AAA),
            IrregularVerb("spring", "spring", "sprang", "sprung", "прыгать, вскакивать", listOf(
                VerbExample("The cat sprang from the chair.", "Кошка прыгнула с кресла."),
                VerbExample("A new idea has sprung to mind.", "Пришла новая идея."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("stand", "stand", "stood", "stood", "стоять", listOf(
                VerbExample("She stood in line for an hour.", "Она простояла в очереди час."),
                VerbExample("He has stood by her through everything.", "Он поддерживал её во всём."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("steal", "steal", "stole", "stolen", "красть, воровать", listOf(
                VerbExample("Someone stole her bag on the bus.", "Кто-то украл её сумку в автобусе."),
                VerbExample("The painting had been stolen.", "Картина была похищена."),
            ), VerbGroup.ABC_O),
            IrregularVerb("stick", "stick", "stuck", "stuck", "прилипать, приклеивать", listOf(
                VerbExample("The label stuck to the bottle.", "Наклейка прилипла к бутылке."),
                VerbExample("The door has stuck and won't open.", "Дверь заклинило, и она не открывается."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("sting", "sting", "stung", "stung", "жалить, колоть", listOf(
                VerbExample("A bee stung him on the arm.", "Пчела ужалила его в руку."),
                VerbExample("Her eyes stung from the smoke.", "Глаза щипало от дыма."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("stink", "stink", "stank", "stunk", "вонять", listOf(
                VerbExample("The rubbish bin stank terribly.", "Мусорное ведро ужасно воняло."),
                VerbExample("His socks had stunk all day.", "От его носков воняло весь день."),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("strike", "strike", "struck", "struck", "ударять, поражать", listOf(
                VerbExample("Lightning struck the old oak tree.", "Молния ударила в старый дуб."),
                VerbExample("A brilliant idea has struck me.", "Меня осенила блестящая идея."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("strive", "strive", "strove", "striven", "стремиться, стараться", listOf(
                VerbExample("She strove to improve her grades.", "Она стремилась улучшить свои оценки."),
                VerbExample("He has always striven for excellence.", "Он всегда стремился к совершенству."),
            ), VerbGroup.ABC_O),
            IrregularVerb("swear", "swear", "swore", "sworn", "клясться, ругаться", listOf(
                VerbExample("He swore to tell the truth.", "Он поклялся говорить правду."),
                VerbExample("She has sworn never to return.", "Она поклялась никогда не возвращаться."),
            ), VerbGroup.ABC_O),
            IrregularVerb("sweep", "sweep", "swept", "swept", "мести, подметать", listOf(
                VerbExample("She swept the kitchen floor.", "Она подмела пол на кухне."),
                VerbExample("The streets have been swept clean.", "Улицы были подметены."),
            ), VerbGroup.ABB_T),
            IrregularVerb("swell", "swell", "swelled", "swollen", "опухать, разбухать", listOf(
                VerbExample("Her ankle swelled after the fall.", "Лодыжка распухла после падения."),
                VerbExample("The river had swollen with rain.", "Река разлилась от дождей."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("swim", "swim", "swam", "swum", "плавать", listOf(
                VerbExample("He swam across the lake.", "Он переплыл озеро."),
                VerbExample("Have you swum in the sea before?", "Ты когда-нибудь плавал в море?"),
            ), VerbGroup.ABC_IAN),
            IrregularVerb("swing", "swing", "swung", "swung", "качаться, размахивать", listOf(
                VerbExample("The children swung on the playground.", "Дети качались на детской площадке."),
                VerbExample("He swung the bat and missed.", "Он замахнулся битой и промахнулся."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("take", "take", "took", "taken", "брать, взять", listOf(
                VerbExample("She took the bus to the airport.", "Она поехала в аэропорт на автобусе."),
                VerbExample("He has taken a new job.", "Он устроился на новую работу."),
            ), VerbGroup.ABC_O),
            IrregularVerb("teach", "teach", "taught", "taught", "учить, преподавать", listOf(
                VerbExample("She taught English for twenty years.", "Она преподавала английский двадцать лет."),
                VerbExample("He has taught me a lot.", "Он многому меня научил."),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("tear", "tear", "tore", "torn", "рвать, разрывать", listOf(
                VerbExample("She tore the letter in half.", "Она разорвала письмо пополам."),
                VerbExample("His jeans had been torn at the knee.", "Его джинсы были порваны на колене."),
            ), VerbGroup.ABC_O),
            IrregularVerb("tell", "tell", "told", "told", "говорить, рассказывать", listOf(
                VerbExample("He told her the good news.", "Он рассказал ей хорошую новость."),
                VerbExample("She has told me everything.", "Она рассказала мне всё."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("think", "think", "thought", "thought", "думать, считать", listOf(
                VerbExample("I thought about the problem all night.", "Я думал об этой проблеме всю ночь."),
                VerbExample("Have you thought about it yet?", "Ты уже думал об этом?"),
            ), VerbGroup.ABB_OUGHT),
            IrregularVerb("throw", "throw", "threw", "thrown", "бросать, кидать", listOf(
                VerbExample("He threw the ball to his teammate.", "Он бросил мяч партнёру по команде."),
                VerbExample("The stone was thrown into the water.", "Камень был брошен в воду."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("thrust", "thrust", "thrust", "thrust", "толкать, совать", listOf(
                VerbExample("She thrust the letter into his hand.", "Она сунула письмо ему в руку."),
                VerbExample("He was thrust into a leadership role.", "Его бросили на руководящую роль."),
            ), VerbGroup.AAA),
            IrregularVerb("tread", "tread", "trod", "trodden", "ступать, топтать", listOf(
                VerbExample("He trod carefully on the ice.", "Он осторожно ступал по льду."),
                VerbExample("The path had been trodden by many feet.", "По тропинке прошло много ног."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("undergo", "undergo", "underwent", "undergone", "подвергаться, проходить", listOf(
                VerbExample("She underwent surgery last week.", "На прошлой неделе ей сделали операцию."),
                VerbExample("The building has undergone major repairs.", "Здание прошло капитальный ремонт."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("understand", "understand", "understood", "understood", "понимать", listOf(
                VerbExample("He finally understood the problem.", "Он наконец понял проблему."),
                VerbExample("Have you understood the instructions?", "Ты понял инструкции?"),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("undo", "undo", "undid", "undone", "развязывать, отменять", listOf(
                VerbExample("She undid the knot with ease.", "Она легко развязала узел."),
                VerbExample("The damage cannot be undone.", "Ущерб нельзя исправить."),
            ), VerbGroup.ABC_OTHER),
            IrregularVerb("unwind", "unwind", "unwound", "unwound", "разматывать; расслабляться", listOf(
                VerbExample("He unwound the cable carefully.", "Он аккуратно размотал кабель."),
                VerbExample("She likes to unwind with a book.", "Она любит расслабиться с книгой."),
            ), VerbGroup.ABB_OUND),
            IrregularVerb("uphold", "uphold", "upheld", "upheld", "поддерживать, отстаивать", listOf(
                VerbExample("The court upheld the decision.", "Суд поддержал решение."),
                VerbExample("The law has been upheld.", "Закон был соблюдён."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("upset", "upset", "upset", "upset", "расстраивать, опрокидывать", listOf(
                VerbExample("The bad news upset her greatly.", "Плохая новость сильно расстроила её."),
                VerbExample("He has upset all our plans.", "Он нарушил все наши планы."),
            ), VerbGroup.AAA),
            IrregularVerb("wake", "wake", "woke", "woken", "будить, просыпаться", listOf(
                VerbExample("She woke up at six every morning.", "Она просыпалась в шесть каждое утро."),
                VerbExample("He has woken up in a bad mood.", "Он проснулся в плохом настроении."),
            ), VerbGroup.ABC_O),
            IrregularVerb("wear", "wear", "wore", "worn", "носить (одежду)", listOf(
                VerbExample("She wore a red dress to the party.", "На вечеринку она была в красном платье."),
                VerbExample("These shoes have been worn every day.", "Эти туфли носят каждый день."),
            ), VerbGroup.ABC_O),
            IrregularVerb("weave", "weave", "wove", "woven", "ткать, плести", listOf(
                VerbExample("She wove a beautiful basket.", "Она сплела красивую корзину."),
                VerbExample("The rug was woven by hand.", "Ковёр был соткан вручную."),
            ), VerbGroup.ABC_O),
            IrregularVerb("weep", "weep", "wept", "wept", "плакать, рыдать", listOf(
                VerbExample("She wept when she heard the news.", "Она заплакала, услышав новость."),
                VerbExample("He had wept silently in the dark.", "Он тихо плакал в темноте."),
            ), VerbGroup.ABB_T),
            IrregularVerb("win", "win", "won", "won", "побеждать, выигрывать", listOf(
                VerbExample("Our team won the championship.", "Наша команда выиграла чемпионат."),
                VerbExample("She has won three gold medals.", "Она выиграла три золотые медали."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("wind", "wind", "wound", "wound", "виться, наматывать", listOf(
                VerbExample("She wound the scarf around her neck.", "Она обмотала шарф вокруг шеи."),
                VerbExample("The road wound through the mountains.", "Дорога петляла среди гор."),
            ), VerbGroup.ABB_OUND),
            IrregularVerb("withdraw", "withdraw", "withdrew", "withdrawn", "отступать, изымать", listOf(
                VerbExample("She withdrew money from the ATM.", "Она сняла деньги в банкомате."),
                VerbExample("The troops had withdrawn from the region.", "Войска отступили из региона."),
            ), VerbGroup.ABC_EWN),
            IrregularVerb("withhold", "withhold", "withheld", "withheld", "удерживать, скрывать", listOf(
                VerbExample("He withheld the information from the press.", "Он скрыл информацию от прессы."),
                VerbExample("Tax has been withheld from his salary.", "Налог был удержан из его зарплаты."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("withstand", "withstand", "withstood", "withstood", "выдерживать, противостоять", listOf(
                VerbExample("The walls withstood the storm.", "Стены выдержали шторм."),
                VerbExample("The bridge has withstood floods for years.", "Мост много лет выдерживает паводки."),
            ), VerbGroup.ABB_OTHER),
            IrregularVerb("wring", "wring", "wrung", "wrung", "выжимать, выкручивать", listOf(
                VerbExample("She wrung out the wet towel.", "Она выжала мокрое полотенце."),
                VerbExample("He wrung his hands in despair.", "Он ломал руки в отчаянии."),
            ), VerbGroup.ABB_UNG),
            IrregularVerb("write", "write", "wrote", "written", "писать", listOf(
                VerbExample("She wrote a novel in six months.", "Она написала роман за шесть месяцев."),
                VerbExample("Have you written the report yet?", "Ты уже написал отчёт?"),
            ), VerbGroup.ABC_O),
        )
    }
}
