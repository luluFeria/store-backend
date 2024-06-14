package com.app.tienda.config;


import org.modelmapper.Condition;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
      return new ModelMapper();
    }

  @Bean
  public ModelMapper modelMapperSkipId() {
    ModelMapper modelMapper = new ModelMapper();

    Condition<?, ?> skipIdCondition = new Condition<Object, Object>() {
      @Override
      public boolean applies(MappingContext<Object, Object> context) {
        return !context.getMapping().getLastDestinationProperty().getName().equals("id");
      }
    };

    modelMapper.getConfiguration().setPropertyCondition(skipIdCondition);
    return modelMapper;
  }
}
