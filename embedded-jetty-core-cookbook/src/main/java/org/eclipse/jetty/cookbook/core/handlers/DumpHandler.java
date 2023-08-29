//
// ========================================================================
// Copyright (c) 1995 Mort Bay Consulting Pty Ltd and others.
//
// This program and the accompanying materials are made available under the
// terms of the Eclipse Public License v. 2.0 which is available at
// https://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
// which is available at https://www.apache.org/licenses/LICENSE-2.0.
//
// SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
// ========================================================================
//

package org.eclipse.jetty.cookbook.core.handlers;

import java.io.OutputStream;
import java.io.PrintWriter;

import org.eclipse.jetty.http.HttpHeader;
import org.eclipse.jetty.io.Content;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Response;
import org.eclipse.jetty.util.Callback;

public class DumpHandler extends Handler.Abstract
{
    @Override
    public boolean handle(Request request, Response response, Callback callback) throws Exception
    {
        response.getHeaders().put(HttpHeader.CONTENT_TYPE, "text/plain; charset=utf-8");

        try (OutputStream stream = Content.Sink.asOutputStream(response);
             PrintWriter out = new PrintWriter(stream))
        {
            out.printf("HttpMethod: %s%n", request.getMethod());
            out.printf("HttpURI: %s%n", request.getHttpURI().toURI().toASCIIString());
            out.printf("HttpVersion: %s%n", request.getConnectionMetaData().getHttpVersion());
        }

        response.write(true, null, callback);
        return true;
    }
}
