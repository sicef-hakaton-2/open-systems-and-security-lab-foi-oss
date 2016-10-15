<?php

namespace AppBundle\Entity;

use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\ORM\Mapping as ORM;
use Gedmo\Timestampable\Traits\TimestampableEntity;
use Zantolov\AppBundle\Entity\Traits\ActivableTrait;
use Zantolov\AppBundle\Entity\Traits\BasicEntityTrait;
use Zantolov\AppBundle\Entity\User;
use Zantolov\MediaBundle\Entity\Traits\ImageableTrait;

/**
 * @ORM\Entity ()
 * @ORM\Table(name="refugees", uniqueConstraints={
 *  @ORM\UniqueConstraint(name="user_idx", columns={"user_id"})
 * })
 * @ORM\HasLifecycleCallbacks
 */
class Refugee implements \JsonSerializable
{
    use BasicEntityTrait;
    use TimestampableEntity;
    use ActivableTrait;
    use ImageableTrait;


    /**
     * @var string
     *
     * @ORM\Column(name="firstName", type="string", length=255)
     */
    private $firstName;

    /**
     * @var string
     *
     * @ORM\Column(name="lastName", type="string", length=255)
     */
    private $lastName;

    /**
     * @var string
     *
     * @ORM\Column(name="externalId", type="string", length=255, nullable=true)
     */
    private $externalId;

    /**
     * @var string
     *
     * @ORM\Column(name="birthDate", type="datetime")
     */
    private $birthDate;

    /**
     * @var string
     *
     * @ORM\Column(name="country", type="string", length=255)
     */
    private $country;


    /**
     * @var User
     * @ORM\OneToOne(targetEntity="Zantolov\AppBundle\Entity\User")
     * @ORM\JoinColumn(name="user_id", referencedColumnName="id", nullable=true)
     */
    private $user;


    /**
     * @var ArrayCollection
     * @ORM\OneToMany(targetEntity="Request", mappedBy="refugee")
     */
    private $requests;


    /**
     * @var ArrayCollection
     * @ORM\OneToMany(targetEntity="Offer", mappedBy="refugee")
     */
    private $acceptedOffers;


    /**
     * @var boolean
     * @ORM\Column(type="boolean", nullable = true)
     */
    private $isMissing;


    /**
     * @var ArrayCollection
     * @ORM\ManyToMany(targetEntity="Zantolov\MediaBundle\Entity\Image", cascade={"persist"})
     * @ORM\JoinTable(name="refugees_images",
     *      joinColumns={@ORM\JoinColumn(name="image_id", referencedColumnName="id", onDelete="CASCADE")},
     *      inverseJoinColumns={@ORM\JoinColumn(name="refugee_id", referencedColumnName="id", onDelete="CASCADE")},
     *      )
     **/
    private $images;

    /**
     * Refugee constructor.
     */
    public function __construct()
    {
        $this->requests = new ArrayCollection();
        $this->images = new ArrayCollection();
    }


    /**
     * @return string
     */
    public function getFirstName()
    {
        return $this->firstName;
    }

    /**
     * @param string $firstName
     */
    public function setFirstName($firstName)
    {
        $this->firstName = $firstName;
    }

    /**
     * @return string
     */
    public function getLastName()
    {
        return $this->lastName;
    }

    /**
     * @param string $lastName
     */
    public function setLastName($lastName)
    {
        $this->lastName = $lastName;
    }

    /**
     * @return string
     */
    public function getExternalId()
    {
        return $this->externalId;
    }

    /**
     * @param string $externalId
     */
    public function setExternalId($externalId)
    {
        $this->externalId = $externalId;
    }

    /**
     * @return string
     */
    public function getBirthDate()
    {
        return $this->birthDate;
    }

    /**
     * @param string $birthDate
     */
    public function setBirthDate($birthDate)
    {
        $this->birthDate = $birthDate;
    }

    /**
     * @return string
     */
    public function getCountry()
    {
        return $this->country;
    }

    /**
     * @param string $country
     */
    public function setCountry($country)
    {
        $this->country = $country;
    }

    /**
     * @return mixed
     */
    public function getUser()
    {
        return $this->user;
    }

    /**
     * @param mixed $user
     */
    public function setUser($user)
    {
        $this->user = $user;
    }

    public function jsonSerialize()
    {
        return array(
            'id'           => $this->getId(),
            'firstName'    => $this->getFirstName(),
            'lastName'     => $this->getLastName(),
            'birthDate'    => $this->getBirthDate()->format(DATE_ATOM),
            'externalId'   => $this->getExternalId(),
            'country'      => $this->getCountry(),
            'createdAt'    => $this->getCreatedAt()->format(DATE_ATOM),
            'profileImage' => '/bundles/app/img/placeholder_user.png',
        );
    }

    /**
     * @return ArrayCollection
     */
    public function getRequests()
    {
        return $this->requests;
    }

    /**
     * @param $request
     */
    public function addRequest($request)
    {
        if (!$this->requests->contains($request)) {
            $this->requests->add($request);
        }
    }

    /**
     * @param $request
     */
    public function removeRequest($request)
    {
        if ($this->requests->contains($request)) {
            $this->requests->removeElement($request);
        }
    }

    public function __toString()
    {
        return sprintf('%s %s', $this->getFirstName(), $this->getLastName());
    }

    /**
     * @return ArrayCollection
     */
    public function getAcceptedOffers()
    {
        return $this->acceptedOffers;
    }

    /**
     * @param ArrayCollection $acceptedOffers
     */
    public function setAcceptedOffers($acceptedOffers)
    {
        $this->acceptedOffers = $acceptedOffers;
    }

    /**
     * @return boolean
     */
    public function isIsMissing()
    {
        return $this->isMissing;
    }

    /**
     * @param boolean $isMissing
     */
    public function setIsMissing($isMissing)
    {
        $this->isMissing = $isMissing;
    }

    /**
     * @return ArrayCollection
     */
    public function getImages()
    {
        return $this->images;
    }

    /**
     * @param ArrayCollection $images
     */
    public function setImages($images)
    {
        $this->images = $images;
    }

}