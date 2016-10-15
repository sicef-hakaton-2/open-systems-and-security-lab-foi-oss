<?php

namespace AppBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Gedmo\Timestampable\Traits\TimestampableEntity;
use Zantolov\AppBundle\Entity\Traits\ActivableTrait;
use Zantolov\AppBundle\Entity\Traits\BasicEntityTrait;
use Zantolov\AppBundle\Entity\User;
use Zantolov\MediaBundle\Entity\Traits\ImageableTrait;

/**
 * @ORM\Entity ()
 * @ORM\Table(name="authorities", uniqueConstraints={
 *  @ORM\UniqueConstraint(name="user_idx", columns={"user_id"})
 * })
 * @ORM\HasLifecycleCallbacks
 */
class Authority implements \JsonSerializable
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
            'country'      => $this->getCountry(),
            'createdAt'    => $this->getCreatedAt()->format(DATE_ATOM),
            'profileImage' => '/bundles/app/img/placeholder_user.png',
        );
    }


}