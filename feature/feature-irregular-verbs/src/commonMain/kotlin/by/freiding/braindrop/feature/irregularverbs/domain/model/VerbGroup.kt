package by.freiding.braindrop.feature.irregularverbs.domain.model

enum class VerbGroup(val title: String, val hint: String) {
    AAA("Без изменений", "все три формы одинаковы: cut / cut / cut"),
    ABA("Как основа", "причастие = основа: run / ran / run"),
    ABB_OUGHT("-ought / -aught", "bring→brought, think→thought, catch→caught"),
    ABB_OUND("-ound", "find→found, bind→bound, wind→wound"),
    ABB_UNG("-ung / -uck", "cling→clung, stick→stuck, spin→spun"),
    ABB_T("-t окончание", "keep→kept, feel→felt, send→sent, burn→burnt"),
    ABB_AID("-aid", "say→said, pay→paid, lay→laid"),
    ABB_OTHER("А=Б, прочие", "past simple = past participle"),
    ABC_IAN("i → a → u", "sing/sang/sung, drink/drank/drunk, swim/swam/swum"),
    ABC_EWN("-ew / -own", "blow/blew/blown, grow/grew/grown, know/knew/known"),
    ABC_O("О-форма прошедшего", "speak/spoke/spoken, take/took/taken, write/wrote/written"),
    ABC_OTHER("А-Б-В, прочие", "все три формы разные"),
}
