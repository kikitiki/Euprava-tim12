package data

import (
	"encoding/json"
	"io"

	"go.mongodb.org/mongo-driver/bson/primitive"
)

type SignInData struct {
	Username string `json:"username" validate:"required"`
	Password string `json:"password" validate:"required"`
}

type User struct {
	ID           primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	Name         string             `bson:"name" json:"name" validate:"required"`
	Surname      string             `bson:"surname" json:"surname" validate:"required"`
	Username     string             `bson:"username" json:"username" validate:"required"`
	Password     string             `bson:"password" json:"password" validate:"required"`
	Age          string             `bson:"age" json:"age" validate:"required"`
	Email        string             `bson:"email" json:"email" validate:"required"`
	Gender       string             `bson:"gender" json:"gender" validate:"required"`
	Residance    string             `bson:"residance" json:"residance" validate:"required"`
	RegisterCode int                `bson:"code" json:"code"`
}

type OpenData struct {
	ID          primitive.ObjectID `bson:"_id,omitempty" json:"id"`
	Name        string             `bson:"name" json:"name" validate:"required"`
	Description string             `bson:"description" json:"description" validate:"required"`
	Context     string             `bson:"context" json:"context" validate:"required"`
}

func (p *SignInData) FromJSON(r io.Reader) error {
	d := json.NewDecoder(r)
	return d.Decode(p)
}

func (p *SignInData) ToJSON(w io.Writer) error {
	e := json.NewEncoder(w)
	return e.Encode(p)
}

func ToJSON(w io.Writer, s string) error {
	e := json.NewEncoder(w)
	return e.Encode(s)
}

type UserRequest struct {
	Username string
}

type Users []*User
type OpenDatas []*OpenData

func (p *Users) ToJSON(w io.Writer) error {
	e := json.NewEncoder(w)
	return e.Encode(p)
}

func (p *User) ToJSON(w io.Writer) error {
	e := json.NewEncoder(w)
	return e.Encode(p)
}

func (p *User) FromJSON(r io.Reader) error {
	d := json.NewDecoder(r)
	return d.Decode(p)
}

func (p *OpenDatas) ToJSON(w io.Writer) error {
	e := json.NewEncoder(w)
	return e.Encode(p)
}

func (p *OpenData) ToJSON(w io.Writer) error {
	e := json.NewEncoder(w)
	return e.Encode(p)
}

func (p *OpenData) FromJSON(r io.Reader) error {
	d := json.NewDecoder(r)
	return d.Decode(p)
}
