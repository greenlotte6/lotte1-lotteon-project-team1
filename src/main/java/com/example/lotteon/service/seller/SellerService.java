package com.example.lotteon.service.seller;

import com.example.lotteon.dto.seller.SellerDTO;
import com.example.lotteon.dto.user.UserDTO;
import com.example.lotteon.entity.seller.Seller;
import com.example.lotteon.entity.seller.SellerCompositeKey;
import com.example.lotteon.entity.user.User;
import com.example.lotteon.exception.EntityAlreadyExistsException;
import com.example.lotteon.repository.seller.SellerRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SellerService {

  private final SellerRepository repo;
  private final ModelMapper mapper;
  private final PasswordEncoder encoder;

  private void encryptPassword(SellerDTO seller) {
    UserDTO user = seller.getSellerId().getUser();
    String encrypted = encoder.encode(user.getPassword());
    user.setPassword(encrypted);
  }

  private Seller toEntity(SellerDTO dto) {
    User user = mapper.map(dto.getSellerId().getUser(), User.class);
    SellerCompositeKey compositeKey = SellerCompositeKey.builder()
        .businessNumber(dto.getSellerId().getBusinessNumber())
        .user(user)
        .build();
    return Seller.builder()
        .sellerCompositeKey(compositeKey)
        .ceo(dto.getCeo())
        .companyName(dto.getCompanyName())
        .sellerNumber(dto.getSellerNumber())
        .fax(dto.getFax())
        .build();
  }

  public List<Seller> getAll() {
    return repo.findAll();
  }

  public void save(SellerDTO sellerDTO) throws EntityAlreadyExistsException {
    String businessNumber = sellerDTO.getSellerId().getBusinessNumber();
    if (repo.existsByBusinessNumber(businessNumber)) {
      String message = String.format("A seller with business number %s already exists",
          businessNumber);
      throw new EntityAlreadyExistsException(message);
    }
    encryptPassword(sellerDTO);
    Seller seller = toEntity(sellerDTO);
    repo.save(seller);
  }
}
