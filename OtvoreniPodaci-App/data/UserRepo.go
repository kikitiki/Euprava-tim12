package data

import (
	"context"
	"fmt"
	"log"
	"os"
	"otvoreni_podaci/utils"
	"time"

	//"github.com/gorilla/sessions"
	"go.mongodb.org/mongo-driver/bson"
	"go.mongodb.org/mongo-driver/bson/primitive"
	"go.mongodb.org/mongo-driver/mongo"
	"go.mongodb.org/mongo-driver/mongo/options"
	"go.mongodb.org/mongo-driver/mongo/readpref"
)

type UserRepo struct {
	cli    *mongo.Client
	logger *log.Logger
}

func New(ctx context.Context, logger *log.Logger) (*UserRepo, error) {
	dburi := os.Getenv("MONGO_DB_URI")

	client, err := mongo.NewClient(options.Client().ApplyURI(dburi))
	if err != nil {
		return nil, err
	}

	err = client.Connect(ctx)
	if err != nil {
		return nil, err
	}

	return &UserRepo{
		cli:    client,
		logger: logger,
	}, nil
}

func (ur *UserRepo) Disconnect(ctx context.Context) error {
	err := ur.cli.Disconnect(ctx)
	if err != nil {
		return err
	}
	return nil
}

func (ur *UserRepo) Ping() {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	err := ur.cli.Ping(ctx, readpref.Primary())
	if err != nil {
		ur.logger.Println(err)
	}

	databases, err := ur.cli.ListDatabaseNames(ctx, bson.M{})
	if err != nil {
		ur.logger.Println(err)
	}
	fmt.Println(databases)
}

func (ur *UserRepo) getCollection() *mongo.Collection {
	userDatabase := ur.cli.Database("user_database")
	usersCollection := userDatabase.Collection("users")
	return usersCollection
}

func (ur *UserRepo) Get(id string) (*User, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	usersCollection := ur.getCollection()

	var user User
	objID, _ := primitive.ObjectIDFromHex(id)
	err := usersCollection.FindOne(ctx, bson.M{"_id": objID}).Decode(&user)
	if err != nil {
		ur.logger.Println(err)
		return nil, err
	}
	return &user, nil
}

func (ur *UserRepo) GetByUsername(name string) (Users, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	usersCollection := ur.getCollection()

	var users Users
	usersCursor, err := usersCollection.Find(ctx, bson.M{"username": name})
	if err != nil {
		ur.logger.Println(err)
		return nil, err
	}
	if err = usersCursor.All(ctx, &users); err != nil {
		ur.logger.Println(err)
		return nil, err
	}
	return users, nil
}

func (ur *UserRepo) GetByDataId(id string) (*OpenData, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	dataCollection := ur.getCollection()

	var data OpenData
	objID, _ := primitive.ObjectIDFromHex(id)
	err := dataCollection.FindOne(ctx, bson.M{"_id": objID}).Decode(&data)
	if err != nil {
		ur.logger.Println(err)
		return nil, err
	}
	return &data, nil
}

func (ur *UserRepo) GetOneDataByDescription(description string) (OpenDatas, error) {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()

	dataCollection := ur.getCollection()

	var data OpenDatas
	usersCursor, err := dataCollection.Find(ctx, bson.M{"description": description})
	if err != nil {
		ur.logger.Println(err)
		return nil, err
	}
	if err = usersCursor.All(ctx, &data); err != nil {
		ur.logger.Println(err)
		return nil, err
	}
	return data, nil
}

func (ur *UserRepo) Post(user *User) error {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	usersCollection := ur.getCollection()

	result, err := usersCollection.InsertOne(ctx, &user)
	if err != nil {
		ur.logger.Println(err)
		return err
	}
	ur.logger.Printf("Documents ID: %v\n", result.InsertedID)
	return nil
}

func (ur *UserRepo) PostData(data *OpenData) error {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	usersCollection := ur.getCollection()
	result, err := usersCollection.InsertOne(ctx, &data)
	if err != nil {
		ur.logger.Println(err)
		return err
	}
	ur.logger.Printf("Documents ID: %v\n", result.InsertedID)
	return nil
}

func (ur *UserRepo) Put(id string, user *User) error {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	usersCollection := ur.getCollection()

	objID, _ := primitive.ObjectIDFromHex(id)
	filter := bson.M{"_id": objID}
	update := bson.M{"$set": bson.M{
		"name":      user.Name,
		"username":  user.Username,
		"password":  user.Password,
		"gender":    user.Gender,
	}}
	result, err := usersCollection.UpdateOne(ctx, filter, update)
	ur.logger.Printf("Documents matched: %v\n", result.MatchedCount)
	ur.logger.Printf("Documents updated: %v\n", result.ModifiedCount)

	if err != nil {
		ur.logger.Println(err)
		return err
	}
	return nil
}

func (ur *UserRepo) PutData(id string, data *OpenData) error {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	usersCollection := ur.getCollection()

	objID, _ := primitive.ObjectIDFromHex(id)
	filter := bson.M{"_id": objID}
	update := bson.M{"$set": bson.M{
		"name":        data.Name,
		"description": data.Description,
		"context":     data.Context,
	}}
	result, err := usersCollection.UpdateOne(ctx, filter, update)
	ur.logger.Printf("Documents matched: %v\n", result.MatchedCount)
	ur.logger.Printf("Documents updated: %v\n", result.ModifiedCount)

	if err != nil {
		ur.logger.Println(err)
		return err
	}
	return nil
}

func (ur *UserRepo) Delete(id string) error {
	ctx, cancel := context.WithTimeout(context.Background(), 5*time.Second)
	defer cancel()
	usersCollection := ur.getCollection()

	objID, _ := primitive.ObjectIDFromHex(id)
	filter := bson.D{{Key: "_id", Value: objID}}
	result, err := usersCollection.DeleteOne(ctx, filter)
	if err != nil {
		ur.logger.Println(err)
		return err
	}
	ur.logger.Printf("Documents deleted: %v\n", result.DeletedCount)
	return nil
}

func (ur *UserRepo) LogInUser(user *SignInData) (string, error) {
	logged, err := ur.GetByUsername(user.Username)
	if err != nil {
		ur.logger.Println(err)
		return "", err
	}

	key := os.Getenv("ACCESS_TOKEN_PRIVATE_KEY")
	var access_token string

	for _, password := range logged {
		if !CheckPasswordHash(user.Password, password.Password) {
			return "wrong", err
		}
		access_token, err = utils.CreateToken(time.Hour, user.Username, key)
		if err != nil {
			ur.logger.Println(err)
			return "", err
		}

	}

	return access_token, nil
}
