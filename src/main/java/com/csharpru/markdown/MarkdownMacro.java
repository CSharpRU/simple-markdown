package com.csharpru.markdown;

import com.atlassian.confluence.content.render.xhtml.ConversionContext;
import com.atlassian.confluence.macro.Macro;
import com.atlassian.confluence.macro.MacroExecutionException;
import org.apache.commons.lang.BooleanUtils;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by csharpru on 22.10.15.
 */
public class MarkdownMacro implements Macro {
    private HashMap<Integer, PegDownProcessor> processors = new HashMap<>();

    @Override
    public String execute(Map parameters, String body, ConversionContext context) throws MacroExecutionException {
        String enablePegDownExtensions = parameters.containsKey("enable-pegdown-extensions") ?
                (String) parameters.get("enable-pegdown-extensions") : "true";
        Boolean extensionsEnabled = BooleanUtils.toBoolean(enablePegDownExtensions);

        return this.getProcessor(extensionsEnabled ? Extensions.ALL : Extensions.NONE).markdownToHtml(body);
    }

    /**
     * Get processor (creates new one if doesn't exist).
     *
     * @param extensions processor extensions
     * @return PegDown processor
     */
    private PegDownProcessor getProcessor(Integer extensions) {
        if (!processors.containsKey(extensions)) {
            processors.put(extensions, new PegDownProcessor(extensions));
        }

        return processors.get(extensions);
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.PLAIN_TEXT;
    }

    @Override
    public OutputType getOutputType() {
        return OutputType.BLOCK;
    }
}
