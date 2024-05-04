package data

import (
	"fmt"

	"github.com/google/uuid"
	"golang.org/x/crypto/bcrypt"
)

const (
	users = "users/%s"
	all   = "users"
)

func generateKey() (string, string) {
	id := uuid.New().String()
	return fmt.Sprintf(users, id), id
}

func constructKey(id string) string {
	return fmt.Sprintf(users, id)
}

func HashPassword(password string) (string, error) {
	bytes, err := bcrypt.GenerateFromPassword([]byte(password), 14)
	return string(bytes), err
}

func CheckPasswordHash(password, hash string) bool {
	err := bcrypt.CompareHashAndPassword([]byte(hash), []byte(password))
	return err == nil
}
