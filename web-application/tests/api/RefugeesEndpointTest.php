<?php


class RefugeesEndpointTest extends \Codeception\TestCase\Test
{
    /**
     * @var \ApiTester
     */
    protected $tester;

    protected function _before()
    {
    }

    protected function _after()
    {
    }

    // tests
    public function testGetIndex()
    {
        $this->tester->haveHttpHeader('Content-type', 'application/json');
        $this->tester->sendGET('/api/refugees/');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->tester->seeResponseJsonMatchesJsonPath('$.status');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].firstName');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].lastName');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.[0].birthDate');
    }


    public function testDetails()
    {
        $this->tester->sendGET('/api/refugees/1');
        $this->tester->seeResponseCodeIs(200);
        $this->tester->seeResponseIsJson();

        $this->tester->seeResponseJsonMatchesJsonPath('$.status');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.firstName');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.lastName');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.birthDate');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.externalId');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.country');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.createdAt');
        $this->tester->seeResponseJsonMatchesJsonPath('$.data.profileImage');
    }
}