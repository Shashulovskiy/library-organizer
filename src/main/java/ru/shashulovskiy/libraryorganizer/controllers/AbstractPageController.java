package ru.shashulovskiy.libraryorganizer.controllers;

import ru.shashulovskiy.libraryorganizer.domain.Librarian;
import ru.shashulovskiy.libraryorganizer.domain.Reader;

import javax.servlet.http.HttpSession;

public class AbstractPageController {
    private static final String AUTHORIZED_READER_SESSION_KEY = "reader";
    private static final String AUTHORIZED_LIBRARIAN_SESSION_KEY = "librarian";

    protected void autorizeReader(final HttpSession session, final Reader reader) {
        if (reader != null) {
            session.setAttribute(AUTHORIZED_READER_SESSION_KEY, reader);
        } else {
            unAuthorizeReader(session);
        }
    }

    protected void authorizeLibrarian(final HttpSession session, final Librarian librarian) {
        if (librarian != null) {
            session.setAttribute(AUTHORIZED_LIBRARIAN_SESSION_KEY, librarian);
        } else {
            unAuthorizeLibrarian(session);
        }
    }

    protected Reader getReader(final HttpSession session) {
        return (Reader) session.getAttribute(AUTHORIZED_READER_SESSION_KEY);
    }

    protected Librarian getLibrarian(final HttpSession session) {
        return (Librarian) session.getAttribute(AUTHORIZED_LIBRARIAN_SESSION_KEY);
    }

    protected void unAuthorizeReader(final HttpSession session) {
        session.removeAttribute(AUTHORIZED_READER_SESSION_KEY);
    }

    protected void unAuthorizeLibrarian(final HttpSession session) {
        session.removeAttribute(AUTHORIZED_LIBRARIAN_SESSION_KEY);
    }

    protected boolean isAutorizedReader(final HttpSession session) {
        return getReader(session) != null;
    }

    protected boolean isAuthorizedLibrarian(final HttpSession session) {
        return getLibrarian(session) != null;
    }
}
