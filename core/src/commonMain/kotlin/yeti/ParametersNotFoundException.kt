package yeti

class ParametersNotFoundException(
    val missing: Set<String>,
    val params: Map<String, Any>
) : RuntimeException(
    """Template Parameters $missing were found in template but their values weren't provided during compilation compilation\nProvided Params are: ${params.entries.joinToString { "${it.key}=${it.value}" }}""".trimIndent()
)