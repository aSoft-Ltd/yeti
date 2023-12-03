package yeti

internal class TemplateCompilerImpl : TemplateCompiler {
    override fun findParameters(input: String): List<Parameter> {
//        val regex = Regex("""\{{2}\s?\w+\s?}{2}""") TODO: Fix this in JS please
        val regex = Regex("""\{\{\s?\w+\s?}}""")
        val params = mutableListOf<Parameter>()
        regex.findAll(input).forEach { m ->
            params.add(Parameter(m.value))
        }
        return params
    }

    override fun compile(input: String, vararg parameters: Pair<String, Any>): String {
        val params = findParameters(input)
        val inputParamMaps = parameters.toMap()
        var output = input

        val missing = buildSet {
            params.forEach { param ->
                if (!inputParamMaps.containsKey(param.name)) {
                    add(param.name)
                }
            }
        }

        if (missing.isNotEmpty()) throw ParametersNotFoundException(missing, inputParamMaps)

        params.forEach { param ->
            output = output.replace(
                oldValue = param.raw,
                newValue = inputParamMaps[param.name].toString()
            )
        }
        return output
    }
}