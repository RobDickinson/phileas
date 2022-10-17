package com.mtnfog.phileas.service.ai;

import com.mtnfog.phileas.model.objects.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Map;

public class OnnxNer {

    private static final Logger LOGGER = LogManager.getLogger(OnnxNer.class);

    private final Inference inference;

    public OnnxNer(File model, File vocab, boolean doLowerCase, final Map<Integer, String> id2Labels) throws Exception {

        this.inference = new Inference(model, vocab, doLowerCase, id2Labels);

    }

    public List<Entity> find(final String tokens, final String context, final String documentId) throws Exception {

        return inference.predict(tokens, context, documentId);

    }

}
