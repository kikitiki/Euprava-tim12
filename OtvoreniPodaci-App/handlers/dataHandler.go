package handlers

import (
	"log"
	"net/http"
	"otvoreni_podaci/data"

	"github.com/gorilla/mux"
	"github.com/gorilla/sessions"
)

type KeyOpenData struct{}

type OpenDataHandler struct {
	logger *log.Logger
	repo   *data.UserRepo
	store  *sessions.CookieStore
}

func NewOpenDataHandler(l *log.Logger, r *data.UserRepo, s *sessions.CookieStore) *OpenDataHandler {
	return &OpenDataHandler{l, r, s}
}

func (p *OpenDataHandler) MiddlewareContentTypeSet(next http.Handler) http.Handler {
	return http.HandlerFunc(func(rw http.ResponseWriter, h *http.Request) {
		p.logger.Println("Method [", h.Method, "] - Hit path :", h.URL.Path)

		rw.Header().Add("Content-Type", "application/json")

		next.ServeHTTP(rw, h)
	})
}

func (p *OpenDataHandler) GetOneDataById(rw http.ResponseWriter, h *http.Request) {
	vars := mux.Vars(h)
	id := vars["id"]

	user, err := p.repo.Get(id)
	if err != nil {
		http.Error(rw, "Database exception", http.StatusInternalServerError)
		p.logger.Fatal("Database exception: ", err)
	}

	if user == nil {
		http.Error(rw, "User with given id not found", http.StatusNotFound)
		p.logger.Printf("User with id: '%s' not found", id)
		return
	}

	err = user.ToJSON(rw)
	if err != nil {
		http.Error(rw, "Unable to convert to json", http.StatusInternalServerError)
		p.logger.Fatal("Unable to convert to json :", err)
		return
	}
}

func (p *OpenDataHandler) GetOneDataByContext(rw http.ResponseWriter, h *http.Request) {
	vars := mux.Vars(h)
	username := vars["username"]

	user, err := p.repo.GetByUsername(username)
	if err != nil {
		http.Error(rw, "Database exception", http.StatusInternalServerError)
		p.logger.Fatal("Database exception: ", err)
	}

	if user == nil {
		http.Error(rw, "User with given id not found", http.StatusNotFound)
		p.logger.Printf("User with id: '%s' not found", username)
		return
	}

	err = user.ToJSON(rw)
	if err != nil {
		http.Error(rw, "Unable to convert to json", http.StatusInternalServerError)
		p.logger.Fatal("Unable to convert to json :", err)
		return
	}
}

func (p *OpenDataHandler) PostUser(rw http.ResponseWriter, h *http.Request) {
	user := h.Context().Value(KeyOpenData{}).(*data.User)
	p.repo.Post(user)
	rw.WriteHeader(http.StatusCreated)
}

func (p *OpenDataHandler) PostData(rw http.ResponseWriter, h *http.Request) {
	data := h.Context().Value(KeyOpenData{}).(*data.OpenData)
	p.repo.PostData(data)
	rw.WriteHeader(http.StatusCreated)
}

func (p *OpenDataHandler) PutUser(rw http.ResponseWriter, h *http.Request) {
	vars := mux.Vars(h)
	id := vars["id"]

	user := h.Context().Value(KeyOpenData{}).(*data.User)

	p.repo.Put(id, user)
	rw.WriteHeader(http.StatusOK)
}

func (p *OpenDataHandler) PutData(rw http.ResponseWriter, h *http.Request) {
	vars := mux.Vars(h)
	id := vars["id"]

	data := h.Context().Value(KeyOpenData{}).(*data.OpenData)

	p.repo.PutData(id, data)
	rw.WriteHeader(http.StatusOK)
}

func (p *OpenDataHandler) DeleteUser(rw http.ResponseWriter, h *http.Request) {
	vars := mux.Vars(h)
	id := vars["id"]

	err := p.repo.Delete(id)

	if err != nil {
		http.Error(rw, err.Error(), http.StatusBadRequest)
		p.logger.Fatal("Unable to delete user.", err)
		return
	}

	rw.WriteHeader(http.StatusOK)
}

func (p *OpenDataHandler) DeleteData(rw http.ResponseWriter, h *http.Request) {
	vars := mux.Vars(h)
	id := vars["id"]
	err := p.repo.Delete(id)

	if err != nil {
		http.Error(rw, err.Error(), http.StatusBadRequest)
		p.logger.Fatal("Unable to delete user.", err)
		return
	}

	rw.WriteHeader(http.StatusOK)
}

func (p *OpenDataHandler) LogInUser(rw http.ResponseWriter, h *http.Request) {
	session, err := p.store.Get(h, "super-secret")
	if err != nil {
		http.Error(rw, "Unable to get cookie store", http.StatusInternalServerError)
		p.logger.Fatal("Unable to get cookie store :", err)
		return
	}

	user := h.Context().Value(KeyOpenData{}).(*data.SignInData)
	
	atoken, err := p.repo.LogInUser(user)
	if err != nil {
		http.Error(rw, "Unable to log in", http.StatusInternalServerError)
		p.logger.Fatal("Unable to log in :", err)
		return
	}
	if (atoken == "wrong") {
		http.Error(rw, "Invalid username or password: ", http.StatusInternalServerError)
		p.logger.Fatal("Invalid username or password: ", err)
		return
	}

	session.Values["access_token"] = atoken
	session.Values["auth"] = "true"
	session.Options.Path = "/"
	session.Options.Secure = false
	session.Options.HttpOnly = false
	session.Values["user"] = user.Username
	session.Options.MaxAge = 1800

	err = session.Save(h, rw)
	if err != nil {
		http.Error(rw, "Unable to save cookies", http.StatusInternalServerError)
		p.logger.Fatal("Unable to save cookies :", err)
		return
	}

	err = data.ToJSON(rw, atoken)
	if err != nil {
		http.Error(rw, "Unable to convert token to json", http.StatusInternalServerError)
		p.logger.Fatal("Unable to convert token to json :", err)
		return
	}
}

func (p *OpenDataHandler) LogoutUser(rw http.ResponseWriter, h *http.Request) {
	session, err := p.store.Get(h, "super-secret")
	if err != nil {
		http.Error(rw, "Unable to get cookies", http.StatusInternalServerError)
		p.logger.Fatal("Unable to get cookies :", err)
		return
	}
	session.Values["access_token"] = ""
	session.Values["refresh_token"] = ""
	session.Values["auth"] = "false"
	session.Values["user"] = ""
	session.Options.MaxAge = -1
	err = session.Save(h, rw)
	if err != nil {
		http.Error(rw, "Unable to save cookies", http.StatusInternalServerError)
		p.logger.Fatal("Unable to save cookies :", err)
		return
	}
	rw.WriteHeader(http.StatusOK)
}