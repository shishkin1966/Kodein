package shishkin.sl.kodeinpsb.common

fun String.pos(initialString: String?, which: String?, start: Int): Int {
    if (initialString == null || which == null) {
        return -1;
    }
    return if (start >= initialString.length) -1 else initialString.indexOf(which, start);
}

fun pos(initialString: String?, which: String?): Int {
    return if (initialString == null || which == null) -1 else initialString.indexOf(which);
}
