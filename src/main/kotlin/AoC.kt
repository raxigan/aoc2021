
fun String.read() = object {}::class.java.getResource(this).readText(Charsets.UTF_8).lines()
