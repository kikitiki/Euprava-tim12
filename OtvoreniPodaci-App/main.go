package main

import (
	"context"
	"log"
	"net/http"
	"os"
	"otvoreni_podaci/data"
	"otvoreni_podaci/handlers"
	"os/signal"
	"syscall"
	"time"

	gorillaHandlers "github.com/gorilla/handlers"
	"github.com/gorilla/mux"
	"github.com/gorilla/sessions"
)

func main() {
	logger := log.New(os.Stdout, "[user-api] ", log.LstdFlags)
	storeLogger := log.New(os.Stdout, "[user-store] ", log.LstdFlags)

	port := os.Getenv("USER_SERVICE_PORT")
	if len(port) == 0 {
		port = "8080"
	}

	timeoutContext, cancel := context.WithTimeout(context.Background(), 30*time.Second)
	defer cancel()

	store, err := data.New(timeoutContext, storeLogger)
	if err != nil {
		logger.Fatal(err)
	}
	defer store.Disconnect(timeoutContext)

	store.Ping()

	cookies := sessions.NewCookieStore([]byte("super-secret"))
	cookies.Options = &sessions.Options{
		Path:     "/",
		MaxAge:   60 * 15,
		HttpOnly: true,
	}

	dataHandler := handlers.NewOpenDataHandler(logger, store, cookies)

	router := mux.NewRouter()
	router.Use(dataHandler.MiddlewareContentTypeSet)

	getRouter := router.Methods(http.MethodGet).Subrouter()
	getRouter.HandleFunc("/{context}/", dataHandler.GetOneDataByContext)


	cors := gorillaHandlers.CORS(gorillaHandlers.AllowedOrigins([]string{"http://localhost:4200"}))
	cors = gorillaHandlers.CORS(gorillaHandlers.AllowCredentials())

	server := http.Server{
		Addr:         ":" + port,        
		Handler:      cors(router),      
		IdleTimeout:  120 * time.Second, 
		ReadTimeout:  5 * time.Second,   
		WriteTimeout: 5 * time.Second,   
	}

	logger.Println("Server listening on port", port)

	go func() {
		err := server.ListenAndServe()
		if err != nil {
			logger.Fatal(err)
		}
	}()

	sigCh := make(chan os.Signal)
	signal.Notify(sigCh, syscall.SIGINT)
	signal.Notify(sigCh, syscall.SIGKILL)

	sig := <-sigCh
	logger.Println("Received terminate, graceful shutdown", sig)

	if server.Shutdown(timeoutContext) != nil {
		logger.Fatal("Cannot gracefully shutdown...")
	}
}