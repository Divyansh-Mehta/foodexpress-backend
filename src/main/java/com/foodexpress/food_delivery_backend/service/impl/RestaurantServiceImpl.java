package com.foodexpress.food_delivery_backend.service.impl;

import com.foodexpress.food_delivery_backend.dto.CreateRestaurantReqDto;
import com.foodexpress.food_delivery_backend.model.Address;
import com.foodexpress.food_delivery_backend.model.Restaurant;
import com.foodexpress.food_delivery_backend.model.User;
import com.foodexpress.food_delivery_backend.repository.AddressRepository;
import com.foodexpress.food_delivery_backend.repository.RestaurantRepository;
import com.foodexpress.food_delivery_backend.repository.UserRepository;
import com.foodexpress.food_delivery_backend.service.RestaurantService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.restaurantRepository = restaurantRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Restaurant createRestaurant(CreateRestaurantReqDto restaurantReqDto, User user) {
        Address address = addressRepository.save(restaurantReqDto.getAddress());

        Restaurant restaurant = new Restaurant();
        restaurant.setAddress(address);
        restaurant.setContactInformation(restaurantReqDto.getContactInformation());
        restaurant.setCuisineType(restaurantReqDto.getCuisineType());
        restaurant.setDescription(restaurantReqDto.getDescription());
        restaurant.setImages(restaurantReqDto.getImages());
        restaurant.setName(restaurantReqDto.getName());
        restaurant.setOpeningHours(restaurantReqDto.getOpeningHours());
        restaurant.setRegistrationDate(LocalDateTime.now());
        restaurant.setOwner(user);

        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant updateRestaurant(Long id, CreateRestaurantReqDto updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        if (updatedRestaurant.getCuisineType() != null){
            restaurant.setCuisineType(updatedRestaurant.getCuisineType());
        }
        if (updatedRestaurant.getDescription() != null){
            restaurant.setDescription(updatedRestaurant.getDescription());
        }
        if (updatedRestaurant.getName() != null){
            restaurant.setName(updatedRestaurant.getName());
        }

        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.delete(restaurant);
    }

    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurant(String keyword) {
        return restaurantRepository.findBySearchQuery(keyword);
    }

    @Override
    public Restaurant findRestaurantById(Long id) throws Exception {
        Optional<Restaurant> opt = restaurantRepository.findById(id);
        if(!opt.isPresent()){
            throw new Exception("Restaurant not found with id " + id);
        }
        return opt.get();
    }

    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        Restaurant restaurant = restaurantRepository.findByOwnerId(userId);
        if (restaurant == null){
            throw new Exception("Restaurant not found with owner id " + userId);
        }
        return restaurant;
    }

    @Override
    public void addtoFavorites(Long resId, User user) throws Exception {
        Restaurant restaurant = findRestaurantById(resId);
        if(user.getFavorites().contains(restaurant)){
            user.getFavorites().remove(restaurant);
        }
        else{
            user.getFavorites().add(restaurant);
        }

        userRepository.save(user);
    }

    @Override
    public void updateRestaurantStatus(Long id) throws Exception {
        Restaurant restaurant = findRestaurantById(id);
        restaurant.setOpen(!restaurant.isOpen());
        restaurantRepository.save(restaurant);
    }
}
