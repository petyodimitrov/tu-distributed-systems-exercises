package com.musala.samples.jhipster.microservices.dealers.web.rest;

import com.musala.samples.jhipster.microservices.dealers.domain.Dealer;
import com.musala.samples.jhipster.microservices.dealers.repository.DealerRepository;
import com.musala.samples.jhipster.microservices.dealers.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.musala.samples.jhipster.microservices.dealers.domain.Dealer}.
 */
@RestController
@RequestMapping("/api")
public class DealerResource {

    private final Logger log = LoggerFactory.getLogger(DealerResource.class);

    private static final String ENTITY_NAME = "dealersDealer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DealerRepository dealerRepository;

    public DealerResource(DealerRepository dealerRepository) {
        this.dealerRepository = dealerRepository;
    }

    /**
     * {@code POST  /dealers} : Create a new dealer.
     *
     * @param dealer the dealer to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new dealer, or with status {@code 400 (Bad Request)} if the dealer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/dealers")
    public ResponseEntity<Dealer> createDealer(@Valid @RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to save Dealer : {}", dealer);
        if (dealer.getId() != null) {
            throw new BadRequestAlertException("A new dealer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Dealer result = dealerRepository.save(dealer);
        return ResponseEntity.created(new URI("/api/dealers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /dealers} : Updates an existing dealer.
     *
     * @param dealer the dealer to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated dealer,
     * or with status {@code 400 (Bad Request)} if the dealer is not valid,
     * or with status {@code 500 (Internal Server Error)} if the dealer couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/dealers")
    public ResponseEntity<Dealer> updateDealer(@Valid @RequestBody Dealer dealer) throws URISyntaxException {
        log.debug("REST request to update Dealer : {}", dealer);
        if (dealer.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Dealer result = dealerRepository.save(dealer);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, dealer.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /dealers} : get all the dealers.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of dealers in body.
     */
    @GetMapping("/dealers")
    public ResponseEntity<List<Dealer>> getAllDealers(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Dealers");
        Page<Dealer> page = dealerRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /dealers/:id} : get the "id" dealer.
     *
     * @param id the id of the dealer to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the dealer, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/dealers/{id}")
    public ResponseEntity<Dealer> getDealer(@PathVariable String id) {
        log.debug("REST request to get Dealer : {}", id);
        Optional<Dealer> dealer = dealerRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(dealer);
    }

    /**
     * {@code DELETE  /dealers/:id} : delete the "id" dealer.
     *
     * @param id the id of the dealer to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/dealers/{id}")
    public ResponseEntity<Void> deleteDealer(@PathVariable String id) {
        log.debug("REST request to delete Dealer : {}", id);
        dealerRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id)).build();
    }
}
