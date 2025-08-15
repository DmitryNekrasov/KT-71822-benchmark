package benchmark.intersection

internal fun <T> Iterable<T>.convertToListIfNotCollection(): Collection<T> =
    if (this is Collection) this else toList()

infix fun <T> Iterable<T>.intersect71822(other: Iterable<T>): Set<T> {
    val otherCollection = other.convertToListIfNotCollection()
    val set = mutableSetOf<T>()
    for (e in this) {
        if (otherCollection.contains(e)) {
            set.add(e)
        }
    }
    return set
}

infix fun <T> Iterable<T>.intersectOpt(other: Iterable<T>): Set<T> {
    val set = mutableSetOf<T>()
    for (e in this) {
        for (o in other) {
            if (e == o) {
                set.add(e)
                break
            }
        }
    }
    return set
}

infix fun <T> Iterable<T>.intersectOpt(other: Collection<T>): Set<T> {
    val set = mutableSetOf<T>()
    for (e in this) {
        if (other.contains(e)) {
            set.add(e)
        }
    }
    return set
}
